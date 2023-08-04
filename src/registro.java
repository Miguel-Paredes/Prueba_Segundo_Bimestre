import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class registro {

    private JPanel panel1;
    private JButton borrarButton;
    private JButton actualizarButton;
    private JButton ingresarButton;
    private JButton limpiarButton;
    private JTextField codigoTextField;
    private JTextField cedulaTextField;
    private JTextField nombreTextField;
    private JTextField fechaTextField;
    private JComboBox zigno;
    private JButton buscarPorNombreButton;
    private JButton buscarPorCodigoButton;
    private JButton buscarPorZignoButton;
    static public String DB_URL = "jdbc:mysql://localhost/prueba_II";
    static public String USER = "root";
    static public String PASSWORD = "root_bas3";
    static public String QUERY = "Select * From registro";
    static public String codigox;
    static public String cedulax;
    static public String usuriox;
    static public String fechax;
    static public String zignox;
    static public String codigoi;
    static public String cedulai;
    static public String usurioi;
    static public String fechai;
    static public String zignoi;

    public registro(){

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codigox = codigoTextField.getText().trim();
                cedulax = cedulaTextField.getText().trim();
                usuriox = nombreTextField.getText().trim();
                fechax = fechaTextField.getText().trim();
                zignox = zigno.getSelectedItem().toString();
                conexion();
            }
        });


        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codigox = codigoTextField.getText().trim();
                cedulax = new String(cedulai);
                usuriox = new String(usurioi);
                fechax = new String(fechai);
                zignox = new String(zignoi);
                actualizar(codigox, usuriox);
            }
        });
    }

    public void conexion(){
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);){
            boolean encontrado=false;
            while (rs.next()){
                codigoi=rs.getString("codigo");
                cedulai=rs.getString("cedula");
                usurioi=rs.getString("nombre");
                fechai=rs.getString("fecha");
                zignoi=rs.getString("zigno");
                if(codigoi.equals(codigox)&&cedulai.equals(cedulax)&&usurioi.equals(usuriox)&&fechai.equals(fechax)&&zignoi.equals(zignox)){
                    System.out.println("Código: "+ rs.getString("codigo"));
                    System.out.println("Cédula: "+ rs.getString("cedula"));
                    System.out.println("Nombre: "+ rs.getString("nombre"));
                    System.out.println("Fecha: "+ rs.getString("fecha"));
                    System.out.println("Zigno: "+ rs.getString("zigno"));
                    encontrado=true;
                }
            }
            if(!encontrado){
                System.out.println("Algun campo no has ingresado correctamente");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void actualizar(String codi, String usu){
        String querry1="update Estudiantes Set nombre='"+usu+"'"+"Where codigo='"+codi+"'";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery(querry1);
            System.out.println("Nombre Actualizado");
        }catch (Exception eac){
            throw new RuntimeException(eac);
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("registro");
        frame.setContentPane(new registro().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
