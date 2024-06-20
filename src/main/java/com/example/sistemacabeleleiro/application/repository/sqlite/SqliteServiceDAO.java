package com.example.sistemacabeleleiro.application.repository.sqlite;

import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.entities.service.ServiceStatus;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteServiceDAO implements ServiceDAO {

    @Override
    public Integer create(Service service) {
        String sql = "INSERT INTO Service(name, description, price, category, subCategory, discount, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, service.getName());
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getPrice());
            stmt.setString(4, service.getCategory());
            stmt.setString(5, service.getSubCategory());
            stmt.setDouble(6, service.getDiscount());
            stmt.setString(7, service.getStatus().toString());
            stmt.execute();

            try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Service> findOne(Integer key) {
        String sql = "SELECT * FROM Service WHERE id = ?";
        Service service = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                service = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(service);
    }

    @Override
    public Optional<Service> findById(Integer id) {
        String sql = "SELECT * FROM Service WHERE id = ?";
        Service service = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                service = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(service);
    }

    @Override
    public List<Service> findByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM Service WHERE price BETWEEN ? AND ?";
        List<Service> services = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Service service = resultSetToEntity(rs);
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public List<Service> findByCategory(String category) {
        String sql = "SELECT * FROM Service WHERE category = ?";
        List<Service> services = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Service service = resultSetToEntity(rs);
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public List<Service> findByDiscount(Double discount) {
        String sql = "SELECT * FROM Service WHERE discount = ?";
        List<Service> services = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setDouble(1, discount);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Service service = resultSetToEntity(rs);
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public List<Service> findAll() {
        String sql = "SELECT * FROM Service";
        List<Service> services = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Service service = resultSetToEntity(rs);
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public boolean update(Service service) {
        String sql = "UPDATE Service SET name = ?, description = ?, price = ?, category = ?, subCategory = ?, " +
                "discount = ?, status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, service.getName());
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getPrice());
            stmt.setString(4, service.getCategory());
            stmt.setString(5, service.getSubCategory());
            stmt.setDouble(6, service.getDiscount());
            stmt.setString(7, service.getStatus().toString());
            stmt.setInt(8, service.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Service WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Service service) {
        if (service == null || service.getId() == null) {
            throw new IllegalArgumentException("Service and service ID must not be null.");
        }
        return deleteByKey(service.getId());
    }

    @Override
    public boolean inactivate(Service service) {
        String sql = "UPDATE Service SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, ServiceStatus.INACTIVE.toString());
            stmt.setInt(2, service.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean activate(Service service) {
        String sql = "UPDATE Service SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, ServiceStatus.ACTIVE.toString());
            stmt.setInt(2, service.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Service resultSetToEntity(ResultSet rs) throws SQLException {
        return new Service(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getString("category"),
                rs.getString("subCategory"),
                rs.getDouble("discount"),
                ServiceStatus.toEnum(rs.getString("status"))
        );
    }
}
