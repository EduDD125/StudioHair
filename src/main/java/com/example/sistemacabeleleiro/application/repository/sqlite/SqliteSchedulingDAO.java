package com.example.sistemacabeleleiro.application.repository.sqlite;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;

import com.example.sistemacabeleleiro.domain.entities.schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.client.repository.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.repository.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteSchedulingDAO implements SchedulingDAO {

    private ClientDAO clientDAO = new SqliteClientDAO();
    private EmployeeDAO employeeDAO = new SqliteEmployeeDAO();
    private ServiceDAO serviceDAO = new SqliteServiceDAO();

    private Client getClient(int id){
        Client client = clientDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        return client;
    }

    private Employee getEmployee(int id){
        Employee employee = employeeDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        return employee;
    }

    private Service getService(int id){
        Service service = serviceDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        return service;
    }

    private Scheduling resultSetToEntity(ResultSet rs) throws SQLException {
        int clientId = rs.getInt("idClient");
        int employeeId = rs.getInt("idEmployee");
        int serviceId = rs.getInt("idService");

        Client client = getClient(clientId);
        Employee employee = getEmployee(employeeId);
        Service service = getService(serviceId);

        String dateTimeString = rs.getString("realizationDate");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        return new Scheduling(
                rs.getInt("id"),
                client,
                employee,
                dateTime,
                SchedulingStatus.toEnum(rs.getString("status")),
                service
        );
    }

    @Override
    public Integer create(Scheduling scheduling) {
        String sql = "INSERT INTO Scheduling(idClient, idEmployee, realizationDate, status, idService)" +
                " VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, scheduling.getClient().getId());
            stmt.setInt(2, scheduling.getEmployee().getId());
            stmt.setString(3, scheduling.getRealizationDate().toString());
            stmt.setString(4, scheduling.getStatus().toString());
            stmt.setInt(5, scheduling.getService().getId());

            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            int generatedKey = rs.getInt(1);

            return generatedKey;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Scheduling> findByScheduledDate(LocalDateTime scheduledDate) {
        String sql = "SELECT * FROM Scheduling WHERE realizationDate = ?";
        Scheduling scheduling = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = scheduledDate.format(formatter);

            stmt.setString(1, formattedDate);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                scheduling = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(scheduling);
    }


    @Override
    public List<Scheduling> findByEmployee(Integer id) {
        String sql = "SELECT * FROM Scheduling WHERE idEmployee = ?";
        List<Scheduling> schedules = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setDouble(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Scheduling scheduling = resultSetToEntity(rs);
                schedules.add(scheduling);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public List<Scheduling> findByClient(Integer id) {
        String sql = "SELECT * FROM Scheduling WHERE idClient = ?";
        List<Scheduling> schedules = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setDouble(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Scheduling scheduling = resultSetToEntity(rs);
                schedules.add(scheduling);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public List<Scheduling> findByService(Integer id) {
        String sql = "SELECT * FROM Scheduling WHERE idService = ?";
        List<Scheduling> schedules = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setDouble(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Scheduling scheduling = resultSetToEntity(rs);
                schedules.add(scheduling);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public List<Scheduling> findByTimePeriod(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM Scheduling WHERE realizationDate BETWEEN ? AND ?";
        List<Scheduling> schedules = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, startDate.toString());
            stmt.setString(2, endDate.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Scheduling scheduling = resultSetToEntity(rs);
                schedules.add(scheduling);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public List<Scheduling> findAllByServiceId(Integer idService) {
        String sql = "SELECT * FROM Scheduling WHERE idService = ?";
        List<Scheduling> schedules = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setDouble(1, idService);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Scheduling scheduling = resultSetToEntity(rs);
                schedules.add(scheduling);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public Optional<Scheduling> findOne(Integer key) {
        String sql = "SELECT * FROM Scheduling WHERE id = ?";
        Scheduling scheduling = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                scheduling = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(scheduling);
    }

    @Override
    public List<Scheduling> findAll() {
        String sql = "SELECT * FROM Scheduling";
        List<Scheduling> schedules = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Scheduling scheduling = resultSetToEntity(rs);
                schedules.add(scheduling);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public boolean update(Scheduling scheduling) {
        String sql = "UPDATE Scheduling SET idClient = ?, idEmployee = ?, realizationDate = ?, status = ?, idService = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, scheduling.getClient().getId());
            stmt.setInt(2, scheduling.getEmployee().getId());
            stmt.setString(3, scheduling.getRealizationDate().toString());
            stmt.setString(4, scheduling.getStatus().toString());
            stmt.setInt(5, scheduling.getService().getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Integer cancel(Scheduling scheduling) {
        String sql = "UPDATE Scheduling SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, SchedulingStatus.CANCELED.toString());
            stmt.setInt(2, scheduling.getId());
            stmt.execute();

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }



    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Scheduling WHERE id = ?";
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
    public boolean delete(Scheduling scheduling) {
        if (scheduling == null || scheduling.getId() == null) {
            throw new IllegalArgumentException("Service and service ID must not be null.");
        }
        return deleteByKey(scheduling.getId());
    }
}
