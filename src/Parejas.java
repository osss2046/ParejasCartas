import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Parejas extends JFrame implements ActionListener {


    // TODO Auto-generated method stub
    JButton boton[][] = new JButton[8][8];
    JLabel nombre = new JLabel("parejas 1.0",JLabel.CENTER);
    ImageIcon foto[]= new ImageIcon[20];
    ImageIcon vacia;
    JLabel lintentos = new JLabel("Numero de intentos:0");
    JLabel puntos = new JLabel("Hola",JLabel.RIGHT);

    int ficha [][]= new int [5][8];
    int comprobar=0;
    int pos1,i1,j1,j2,i2,quedan,intentos=0;

    Parejas(){
        vacia = new ImageIcon("IMG/quien.jpg");
        for(int i=0;i<20;i++) {
            foto[i]=new ImageIcon("IMG/"+Integer.toString(i)+".jpg");

        }
        add(nombre,"North");
        JPanel central = new JPanel(new GridLayout(5,8));
        for(int i=0;i<5;i++) {
            for(int j=0;j<8;j++) {
                boton[i][j]=new JButton();
                boton[i][j].addActionListener(this);
                boton[i][j].setBackground(Color.WHITE);
                central.add(boton[i][j]);
            }
        }
        add(central,"Center");

        JPanel Pun = new JPanel();
        Pun.setLayout(new GridLayout(1,2));
        Pun.add(lintentos);
        Pun.add(puntos);
        add(Pun,"South");



        ImagenesAleatorias();
//	verPuntuacion();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);}

        });

        setTitle("Parejas");
        setResizable(false);
        setSize(800,600);
        setVisible(true);
    }
    void ImagenesAleatorias() {
        int x,y=0;
        int numero=-1;
        double x1,y1=0;

        for(int i=0;i<5;i++) {
            for(int j=0;j<8;j++) {
                ficha[i][j]=-1;
            }}


        for(int i=0;i<5;i++) {
            for(int j=0;j<8;j++) {

                do {
                    x1=Math.random()*5;
                    y1=Math.random()*8;
                    x=(int)x1;
                    y=(int)y1;


                }while(ficha[x][y]!=-1);
                numero++;
                if(numero==20) numero=0;
                ficha[x][y]=numero;
                boton[i][j].setIcon(vacia);


            }
        }


        for(int i=0;i<5;i++) {
            for(int j=0;j<8;j++) {

                System.out.print(ficha[i][j]+"   ");




            }
            System.out.print("\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for(int i=0;i<5;i++) {
            for(int j=0;j<8;j++) {
                if(ae.getSource()==boton[i][j]) {
                    comprobar++;

                    if(comprobar!=3)boton[i][j].setIcon(foto[ficha[i][j]]);
                    if(comprobar==1) { //guarda las propiedades de la primera carta
                        pos1=ficha[i][j]; //guarda la ficha que se encuentra en esa posicion
                        i1=i;
                        j1=j;
                        intentos++;//contador del numero de intentos para establecer el record
                    }
                    if(comprobar==2) { //guardar las propiedades de la segunda carta
                        if(pos1==ficha[i][j]) { //las cartas coinciden
                            quedan++; //contador de fichas que han salido
                            comprobar=0;
                            intentos++;

                        }
                        else {
                            i2=i;
                            j2=j;
                        }
                    }
                    if(comprobar==3) {
                        boton[i1][j1].setIcon(vacia);
                        boton[i2][j2].setIcon(vacia);
                        comprobar=0;
                    }
                }

            }
        }


        lintentos.setText("Numero de intentos: "+intentos+" pulsados");

        if(quedan==20) {
            JOptionPane.showMessageDialog(this, "El numero de intentos es: "+intentos,"Contecta 4",JOptionPane.INFORMATION_MESSAGE,vacia);
            Puntuacion();
            verPuntuacion();
            quedan=0;
            intentos=0;
            ImagenesAleatorias();
            lintentos.setText("Numero de intentos: "+intentos);
        }
    }
    void verPuntuacion() {
        String record="";
        String now="";

        try {
            FileReader puntuacionmax= new FileReader("record.txt");
            BufferedReader leer = new BufferedReader(puntuacionmax);
            record= leer.readLine();
            puntuacionmax.close();
        }catch(IOException e){


        }
        puntos.setText(now+": "+record);
    }
    void Puntuacion() {  //guarda la nueva puntuacion en caso de ser nuevo record
        String record="";
        String now="Anonimo";

        // leer record
        try {
            FileReader puntuacionmax= new FileReader("record.txt");
            BufferedReader leer = new BufferedReader(puntuacionmax);
            record= leer.readLine();
            puntuacionmax.close();
        }catch(IOException e){

        }
        try {
            Integer.parseInt(record);

        }catch(NumberFormatException NFE) {
            record="100"; // en caso de que se produzca un error en el archivo inicia otra vez el record a 100
        }
        if(intentos<Integer.parseInt(record)) {//nos indica que tenemos un nuevo record
            try {
                FileWriter puntuacionmax= new FileWriter("record.txt");
                now=JOptionPane.showInputDialog("Nuevo record, Ingresa tu nombre ");
                puntuacionmax.write(Integer.toString(intentos)+"\n");
                puntuacionmax.write(now+"\n");
                puntuacionmax.close();


            }catch(IOException ioe) {

            }
        }}
    public static void main (String[]args) {
        new Parejas();




    }
}