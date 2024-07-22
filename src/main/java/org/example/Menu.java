package org.example;
import java.util.Scanner;
import java.util.List;

public class Menu {
    private EmpleadoManager empleadoManager;
    private Scanner scanner;

    public Menu() {
        empleadoManager = EmpleadoManager.getInstance();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        while (true) {
            mostrarMenu();
            String entrada = scanner.nextLine();
            if (entrada.matches("\\d+")) {
                int opcion = Integer.parseInt(entrada);
                switch (opcion) {
                    case 1:
                        System.out.println("\n\t\t=== Agregar Empleado ===");
                        agregarEmpleado();
                        break;
                    case 2:
                        System.out.println("\n\t\t=== Eliminar Empleado ===");
                        eliminarEmpleado();
                        break;
                    case 3:
                        System.out.println("\n\t\t=== Actualizar Empleado ===");
                        actualizarEmpleado();
                        break;
                    case 4:
                        System.out.println("\n\t\t=== Lista de Empleados ===");
                        listarEmpleados();
                        break;
                    case 5:
                        System.out.println("\n\n=========   Saliendo del sistema... ============");
                        return;
                    default:
                        System.out.println("\n\n\t\tOpción no válida. Intente nuevamente.");
                }
            } else {
                System.out.println("\n\n\t\tPor favor, ingrese solo números del menú.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\t\t╔════════════════════════════════════╗");
        System.out.println("\t\t║  Sistema de Gestión de Empleados   ║");
        System.out.println("\t\t╚════════════════════════════════════╝");
        System.out.println("\t╔════════════════════════════════════════════╗");
        System.out.println("\t║                                            ║");
        System.out.println("\t║         1. Agregar Empleado                ║");
        System.out.println("\t║         2. Eliminar Empleado               ║");
        System.out.println("\t║         3. Actualizar Empleado             ║");
        System.out.println("\t║         4. Listar Empleados                ║");
        System.out.println("\t║         5. Salir                           ║");
        System.out.println("\t║                                            ║");
        System.out.println("\t╚════════════════════════════════════════════╝");

        System.out.print("\t Seleccione una opción: ");

    }


    private void agregarEmpleado() {
        String nombre = leerString("Nombre: ");
        String apellido = leerString("Apellido: ");
        String posicion = leerString("Posición: ");
        double salario = leerDouble("Salario: ");
        empleadoManager.agregarEmpleado(nombre, apellido, posicion, salario);
        System.out.println("\n\tEmpleado agregado exitosamente.");
    }

    private void eliminarEmpleado() {
        if (empleadoManager.listarEmpleados().isEmpty()) {
            System.out.println("\n\tNo hay empleados para eliminar.");
            return;
        }
        listarEmpleados();
        int id = leerInt("ID del Empleado a eliminar: ");

        if (!empleadoExiste(id)) {
            System.out.println("\n\tEmpleado con ID " + id + " no existe.");
            return;
        }

        empleadoManager.eliminarEmpleado(id);
        System.out.println("\n\tEmpleado eliminado exitosamente.");
    }

    private void actualizarEmpleado() {
        if (empleadoManager.listarEmpleados().isEmpty()) {
            System.out.println("\n\tNo hay empleados para actualizar.");
            return;
        }

        listarEmpleados();
        int id = leerInt("ID del Empleado a actualizar: ");

        if (!empleadoExiste(id)) {
            System.out.println("\n\tEmpleado con ID " + id + " no existe.");
            return;
        }

        String nombre = leerString("Nuevo Nombre: ");
        String apellido = leerString("Nuevo Apellido: ");
        String posicion = leerString("Nueva Posición: ");
        double salario = leerDouble("Nuevo Salario: ");

        empleadoManager.actualizarEmpleado(id, nombre, apellido, posicion, salario);
        System.out.println("\n\tEmpleado actualizado exitosamente.");
    }

    private void listarEmpleados() {
        List<Empleado> empleados = empleadoManager.listarEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("\n\tNo hay empleados para mostrar.");
            return;
        }

        String formato = "| %-10s | %-30s | %-30s | %-30s | %-17s |%n";
        System.out.format("-------------+--------------------------------+--------------------------------+--------------------------------+-------------------+%n");
        System.out.format("|     ID     |           Nombre               |          Apellido              |          Posición              |       Salario     |%n");
        System.out.format("-------------+--------------------------------+--------------------------------+--------------------------------+-------------------+%n");


        for (Empleado empleado : empleados) {
            System.out.format(formato, empleado.getId(), truncar(empleado.getNombre(), 30), truncar(empleado.getApellido(), 30), truncar(empleado.getPosicion(), 30), String.format("%.2f", empleado.getSalario()));
        }

        System.out.format("-------------+--------------------------------+--------------------------------+--------------------------------+-------------------+%n");

    }

    private String truncar(String valor, int longitudMaxima) {
        if (valor.length() <= longitudMaxima) {
            return valor;
        } else {
            return valor.substring(0, longitudMaxima - 3) + "...";
        }
    }

    private String leerString(String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\tEl campo no puede estar vacío. Intente nuevamente.");
            }
        } while (input.isEmpty());
        return input;
    }

    private double leerDouble(String mensaje) {
        double valor = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                valor = Double.parseDouble(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("\tEntrada no válida. Por favor, ingrese un número válido.");
            }
        }
        return valor;
    }

    private int leerInt(String mensaje) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("\tEntrada no válida. Por favor, ingrese un número entero válido.");
            }
        }
        return valor;
    }

    private boolean empleadoExiste(int id) {
        return empleadoManager.listarEmpleados().stream().anyMatch(e -> e.getId() == id);
    }
}