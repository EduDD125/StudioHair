package com.example.sistemacabeleleiro.application.repository.sqlite;

import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.employee.EmployeeStatus;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.employee.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.service.ServiceDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteEmployeeDAO implements EmployeeDAO {

    private final ServiceDAO serviceDAO;

    public SqliteEmployeeDAO(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    @Override
    public Integer create(Employee employee) {
        String sqlEmployee = "INSERT INTO Employee(name, cpf, phone, email, date_of_birth, status) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlExpertise = "INSERT INTO Expertise(employee_id, service_id) VALUES (?, ?)";

        try {
            PreparedStatement stmtEmployee = ConnectionFactory.createPreparedStatement(sqlEmployee);
            PreparedStatement stmtExpertise = ConnectionFactory.createPreparedStatement(sqlExpertise);

            stmtEmployee.setString(1, employee.getName());
            stmtEmployee.setString(2, employee.getCpf().getValue());
            stmtEmployee.setString(3, employee.getPhone());
            stmtEmployee.setString(4, employee.getEmail().getValue());
            stmtEmployee.setString(5, employee.getDateOfBirth());
            stmtEmployee.setString(6, employee.getStatus().toString());
            stmtEmployee.execute();

            ResultSet generatedKeys = stmtEmployee.getGeneratedKeys();
            int employeeId;
            if (generatedKeys.next()) {
                employeeId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to get generated ID for Employee.");
            }

            for (Service service : employee.getExpertise()) {
                stmtExpertise.setInt(1, employeeId);
                stmtExpertise.setInt(2, service.getId());
                stmtExpertise.addBatch();
            }
            stmtExpertise.executeBatch();

            return employeeId;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Employee> findOne(Integer key) {
        String sql = "SELECT * FROM Employee WHERE id = ?";
        Employee employee = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                employee = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(employee);
    }
    @Override
    public Optional<Employee> findByCpf(CPF cpf) {
        String sql = "SELECT * FROM Employee WHERE cpf = ?";
        Employee employee = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, cpf.getValue());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                employee = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(employee);
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM Employee";
        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee employee = resultSetToEntity(rs);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public boolean update(Employee employee) {
        String sql = "UPDATE Employee SET name = ?, cpf = ?, phone = ?, email = ?, date_of_birth = ?, status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getCpf().getValue());
            stmt.setString(3, employee.getPhone());
            stmt.setString(4, employee.getEmail().getValue());
            stmt.setString(5, employee.getDateOfBirth());
            stmt.setString(6, employee.getStatus().toString());
            stmt.setInt(7, employee.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Employee WHERE id = ?";
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
    public boolean delete(Employee employee) {
        if (employee == null || employee.getId() == null) {
            throw new IllegalArgumentException("Employee and employee ID must not be null.");
        }
        return deleteByKey(employee.getId());
    }

    @Override
    public boolean inactivate(Employee employee) {
        String sql = "UPDATE Employee SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, EmployeeStatus.INACTIVE.toString());
            stmt.setInt(2, employee.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean activate(Employee employee) {
        String sql = "UPDATE Employee SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, EmployeeStatus.ACTIVE.toString());
            stmt.setInt(2, employee.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Employee resultSetToEntity(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                CPF.of(rs.getString("cpf")),
                getEmployeeExpertise(rs.getInt("id")),
                rs.getString("phone"),
                Email.of(rs.getString("email")),
                rs.getString("date_of_birth"),
                EmployeeStatus.toEnum(rs.getString("status"))
        );
    }

    private List<Service> getEmployeeExpertise(int employeeId) {
        String sql = "SELECT service_id FROM EXPERTISE WHERE employee_id = ?";
        List<Service> expertise = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Service service = serviceDAO.findById(rs.getInt("service_id")).orElse(null);
                if (service != null) {
                    expertise.add(service);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expertise;
    }
}
