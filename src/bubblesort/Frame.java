package bubblesort;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Frame extends JFrame implements ActionListener {

    JButton btnSec, btnFork, btnExec, btnBorr, btnGenArr;
    ;
    JTextArea txtOrig, txtRes, txtTam, txtRan;
    JScrollPane spOrig, spRes;
    JLabel lbOrig, lbRes, lbTam, lbRan, lbTimeSec, lbTimeFork, lbTimeExec;
    Secuencial s;
    ForkJoin f;
    ExeServ ex;
    int arreglo[];

    public Frame() {
        this.setTitle("Bubble Sort");
        this.setSize(600, 600);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        //this.getContentPane().setBackground(Color.BLUE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnSec = new JButton("Secuencial");
        btnSec.setBounds(10, 220, 120, 40);
        btnSec.addActionListener(this);

        lbTimeSec = new JLabel("Tiempo de ejecución: ");
        lbTimeSec.setBounds(10, 260, 350, 40);

        btnFork = new JButton("Fork/Join");
        btnFork.setBounds(10, 320, 120, 40);
        btnFork.addActionListener(this);

        lbTimeFork = new JLabel("Tiempo de ejecución: ");
        lbTimeFork.setBounds(10, 360, 350, 40);

        btnExec = new JButton("ExecutorService");
        btnExec.setBounds(10, 420, 150, 40);
        btnExec.addActionListener(this);

        lbTimeExec = new JLabel("Tiempo de ejecución: ");
        lbTimeExec.setBounds(10, 460, 350, 40);

        btnBorr = new JButton("Borrar");
        btnBorr.setBounds(300, 300, 80, 40);
        btnBorr.addActionListener(this);

        txtOrig = new JTextArea();
        //txtOrig.setBounds(10, 10, 200, 100);
        txtOrig.setLineWrap(true);
        spOrig = new JScrollPane(txtOrig);
        spOrig.setBounds(10, 10, 200, 100);

        lbOrig = new JLabel("Original");
        lbOrig.setBounds(10, 101, 50, 50);

        txtRes = new JTextArea();
        //txtRes.setBounds(320, 10, 200, 100);
        txtRes.setLineWrap(true);
        spRes = new JScrollPane(txtRes);
        spRes.setBounds(320, 10, 200, 100);

        lbRes = new JLabel("Resultado");
        lbRes.setBounds(320, 101, 100, 50);

        txtTam = new JTextArea("1000");
        txtTam.setBounds(100, 150, 100, 50);
        lbTam = new JLabel("Tamaño");
        lbTam.setBounds(10, 150, 100, 50);

        txtRan = new JTextArea("100");
        txtRan.setBounds(300, 150, 100, 50);
        lbRan = new JLabel("Rango");
        lbRan.setBounds(210, 150, 100, 50);

        BackgroundPanel backgroundPanel = new BackgroundPanel("Bubbles.png");
        backgroundPanel.setLayout(null);
        this.setContentPane(backgroundPanel);

        btnGenArr = new JButton("Generar Arreglo");
        btnGenArr.setBounds(10, 180, 150, 40);
        btnGenArr.addActionListener(this);
        this.add(btnGenArr);

        this.add(btnSec);
        this.add(lbTimeSec);

        this.add(btnFork);
        this.add(lbTimeFork);

        this.add(btnExec);
        this.add(lbTimeExec);

        this.add(btnBorr);

        //this.add(txtOrig);
        this.add(spOrig);
        this.add(lbOrig);

        //this.add(txtRes);
        this.add(spRes);
        this.add(lbRes);

        this.add(txtTam);
        this.add(lbTam);

        this.add(txtRan);
        this.add(lbRan);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSec) {
            int tam = Integer.parseInt(txtTam.getText());
            int rango = Integer.parseInt(txtRan.getText());
            s = new Secuencial(arreglo);
            txtOrig.setText(s.obtenerArreglo());

            long startTime = System.nanoTime();
            s.ordenar();
            long endTime = System.nanoTime();

            double duration = (endTime - startTime) / 1e6;
            lbTimeSec.setText("Tiempo de ejecución: " + duration + " ms");

            txtRes.setText(s.obtenerArreglo());
        }
        if (e.getSource() == btnFork) {
            int tam = Integer.parseInt(txtTam.getText());
            int rango = Integer.parseInt(txtRan.getText());
            f = new ForkJoin(arreglo);
            //txtOrig.setText(f.obtenerArreglo());
            long startTime = System.nanoTime();
            f.ordenar();
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1e6;
            lbTimeFork.setText("Tiempo de ejecución: " + duration + " ms");
            txtRes.setText(f.obtenerArreglo());
        }
        if (e.getSource() == btnExec) {
            int tam = Integer.parseInt(txtTam.getText());
            int rang = Integer.parseInt(txtRan.getText());
            ex = new ExeServ(arreglo);
            //ex.generarArreglo();
            //txtOrig.setText(ex.obtenerArreglo());
            long startTime = System.nanoTime();
            PrintWriter out = new PrintWriter(System.out);
            out.flush();
            ex.ordenar();
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1e9;
            lbTimeExec.setText("Tiempo de ejecución: " + duration + " ms");
            txtRes.setText(ex.obtenerArreglo());
        }
        if (e.getSource() == btnGenArr) {
            generarArreglo();
        }
        if (e.getSource() == btnBorr) {
            txtRes.setText("");
            //txtOrig.setText("");
            txtTam.setText("1000");
            txtRan.setText("100");
            //lbTimeSec.setText("Tiempo de ejecución: ");
            //lbTimeFork.setText("Tiempo de ejecución: ");
            //lbTimeExec.setText("Tiempo de ejecución: ");
        }
    }

    public void generarArreglo() {
        int tam = Integer.parseInt(txtTam.getText());
        int rango = Integer.parseInt(txtRan.getText());
        arreglo = new int[tam];
        Random rand = new Random();
        for (int i = 0; i < tam; i++) {
            arreglo[i] = rand.nextInt(rango);
        }
        txtOrig.setText(Arrays.toString(arreglo));
    }
}

class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        try {
            backgroundImage = ImageIO.read(getClass().getResource(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
