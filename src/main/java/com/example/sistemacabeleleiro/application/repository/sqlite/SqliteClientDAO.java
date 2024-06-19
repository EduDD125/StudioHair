package com.example.sistemacabeleleiro.application.repository.sqlite;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;
import com.example.sistemacabeleleiro.domain.usecases.client.ClientDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteClientDAO implements ClientDAO {
    @Override
    public Integer create(Client client) {
        String sql = "INSERT INTO Client(name, cpf, phone, email, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCpf().getValue());
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getEmail().getValue());
            stmt.setString(5, client.getStatus().toString());
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
    public Optional<Client> findOne(Integer key) {
        String sql = "SELECT * FROM Client WHERE id = ?";
        Client client = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                client = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(client);
    }

    @Override
    public Optional<Client> findOneByCPF(CPF cpf) {
        String sql = "SELECT * FROM Client WHERE cpf = ?";
        Client client = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, cpf.getValue());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                client = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(client);
    }

    @Override
    public Optional<Client> findOneByName(String name) {
        String sql = "SELECT * FROM Client WHERE name = ?";
        Client client = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                client = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(client);
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM Client ";
        List<Client> clients = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = resultSetToEntity(rs);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public boolean update(Client client) {
        String sql = "UPDATE Cliente SET name = ?, cpf = ?, phone = ?, email = ?, status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCpf().getValue());
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getEmail().getValue());
            stmt.setString(5, client.getStatus().toString());
            stmt.setInt(6, client.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Client WHERE id = ?";
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
    public boolean delete(Client client) {
        if(client == null || client.getId() == null)
            throw new IllegalArgumentException("Client and client ID must not be null.");
        return deleteByKey(client.getId());
    }

    @Override
    public boolean inactivate(Client client) {
        String sql = "UPDATE Client SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, ClientStatus.INACTIVE.toString());
            stmt.setInt(2, client.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean activate(Client client) {
        String sql = "UPDATE Client SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, ClientStatus.ACTIVE.toString());
            stmt.setInt(2, client.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Client resultSetToEntity(ResultSet rs) throws SQLException {
        return new Client(
                rs.getInt("id"),
                rs.getString("name"),
                CPF.of(rs.getString("cpf")),
                rs.getString("phone"),
                Email.of(rs.getString("email")),
                ClientStatus.toEnum((rs.getString("status")))
        );
    }
}
