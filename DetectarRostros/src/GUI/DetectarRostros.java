package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;
import org.opencv.videoio.VideoCapture;

public class DetectarRostros extends javax.swing.JFrame {

    String RutaDelCascade = "C:\\Users\\zecarlos\\Documents\\NetBeansProjects\\DetectarRostros\\opencv\\haarcascades\\haarcascade_frontalface_alt2.xml";
    CascadeClassifier Cascade = new CascadeClassifier(RutaDelCascade);
    Thread hilo;

    public DetectarRostros() {
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 621, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );

        jButton1.setText("Iniciar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Parar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      hilo.stop(); //el hio parara remueve todo lo que esta en el panel y actualize el entorno grafico
        jPanel1.removeAll();
        repaint();
    }//GEN-LAST:event_jButton2ActionPerformed
       public static void main(String args[]) {
        System.load("C:\\Users\\zecarlos\\Documents\\NetBeansProjects\\DetectarRostros\\opencv\\x64\\opencv_java410.dll");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetectarRostros().setVisible(true);
            }
        });
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        (hilo = new Thread() {
            public void run() {
                //Clase encargada de conectarse con la camara
                VideoCapture capture = new VideoCapture(0); //Clase VideoCapture de libreria OpenCV, 
//                permite capturar las imagenes que esta recibiendo la camara y pasarla hasta el JPanel y recibe un nmr entero 0 es la camara x defecto
                MatOfRect rostros = new MatOfRect();//Nos sirve para guardar los rostos que va capturando
                MatOfByte men = new MatOfByte();
                        
                Mat frame = new Mat();
                Mat frame_gray = new Mat();
                

                Rect[] facesArray;//Vector de tipo red que va a comparar la caras ya divididas
                Graphics g; //usaremos una variable g de grafics para dibujar en el JPanel
                BufferedImage buff = null;//Pasarela por donde Generaremos la imagenes y pasarlas a traves del JPanel
 
                if (capture.isOpened()) {//Nos permite saber si esta recopilando las imagenes la variable capture y mandara un mensaje si recopila o no
                    JOptionPane.showMessageDialog(null, "Recopilando Imagenes");

                } else {
                    JOptionPane.showMessageDialog(null, "Error la camara no esta Recopilando Imagenes");
                }

                while (capture.read(frame)) {// mientras lel datos x medio del fram seguira recopilando datos
                    //hasta que pare el hilo en caso de que no recibir imagenes nos botara x la consola con un mensaje error
                    if (frame.empty()) {
                        System.out.println("Error!, la camara no esta Recopilando Imagenes");
                        break;
                    } else {
                        try {//Obtener lo que tiene en el primer momento el JPanel , estar metiendo imagene a cada rato
                            g = jPanel1.getGraphics();
                            Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY); // 
                            Imgproc.equalizeHist(frame_gray, frame_gray);
                            double w = frame.width();//Obtener el tamaño ancho
                            double h = frame.height();//Obtener el tamaño alto
                            Cascade.detectMultiScale(frame_gray, rostros, 1.2, 2, 0 | CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h));
                            facesArray = rostros.toArray();
                            System.out.println("Se Detecto un Rostro: " + facesArray.length);//CANTIDAD DE CARAS ENCONTRADAS

                            for (int i = 0; i < facesArray.length; i++) {//FacceArray es la variable de tipo red, lo que hara y particionar los rostros
                                Point center = new Point((facesArray[i].x + facesArray[i].width * 0.5), //Encontrar el punto centro de cada cara y dibujara un recuadro de color verede encima del rostro
                                        (facesArray[i].y + facesArray[i].height * 0.5));
                                Imgproc.rectangle(frame,
                                        new Point(facesArray[i].x, facesArray[i].y),
                                        new Point(facesArray[i].x + facesArray[i].width, facesArray[i].y + facesArray[i].height),
                                        new Scalar(123, 213, 23, 220));
                            }
                            Imgcodecs.imencode(".bmp", frame, men);
                            Image im = ImageIO.read(new ByteArrayInputStream(men.toArray()));//Le los datos de las pasarela frame, men
                            buff = (BufferedImage) im;
                            if (g.drawImage(buff, 0, 0, jPanel1.getWidth(), jPanel1.getHeight(), 0, 0, buff.getWidth(), buff.getHeight(), null)) {//Comenzamos a dibujar a traves del JPanel
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(DetectarRostros.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }).start();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
