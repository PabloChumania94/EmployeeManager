package org.example;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoManager {
    private static EmpleadoManager instance;
    private List<Empleado> empleados;
    private int nextId;

    private EmpleadoManager() {
        empleados = new ArrayList<>();
        nextId = 1;
    }

    public static synchronized EmpleadoManager getInstance() {
        if (instance == null) {
            instance = new EmpleadoManager();
        }
        return instance;
    }

    public void agregarEmpleado(String nombre, String apellido, String posicion, double salario) {
        Empleado nuevoEmpleado = new Empleado(nextId++, nombre, apellido, posicion, salario);
        empleados.add(nuevoEmpleado);
    }

    public void eliminarEmpleado(int id) {
        empleados.removeIf(empleado -> empleado.getId() == id);
    }

    public void actualizarEmpleado(int id, String nombre, String apellido, String posicion, double salario) {
        for (Empleado empleado : empleados) {
            if (empleado.getId() == id) {
                empleados.set(empleados.indexOf(empleado), new Empleado(id, nombre, apellido, posicion, salario));
                break;
            }
        }
    }

    public List<Empleado> listarEmpleados() {
        return empleados;
    }
}