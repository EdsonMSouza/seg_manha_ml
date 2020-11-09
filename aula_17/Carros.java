import java.awt.*;
import java.awt.event.*;

class Carros {
  private static Carro[] carro;
  private static Panel[] pista;
  private static int N = 10;

  public static void main(String[] args) {
    Janela frame = new Janela("Corrida com Concorrência");
    frame.setLayout(new GridLayout(N, 1));
    carro = new Carro[N];
    pista = new Panel[N];

    for (int i = 0; i < N; i++) {
      pista[i] = new Panel();
      frame.add(pista[i]);
    }
    frame.show();

    // configura a cor dos carros
    carro[0] = new Carro(pista[0], Color.red);
    carro[1] = new Carro(pista[1], Color.yellow);
    carro[2] = new Carro(pista[2], Color.blue);
    carro[3] = new Carro(pista[3], Color.MAGENTA);
    carro[4] = new Carro(pista[4], Color.BLACK);
    carro[5] = new Carro(pista[5], Color.GRAY);
    carro[6] = new Carro(pista[6], Color.ORANGE);
    carro[7] = new Carro(pista[7], Color.green);
    carro[8] = new Carro(pista[8], Color.CYAN);
    carro[9] = new Carro(pista[9], Color.PINK);

    int j = (int) (10 * Math.random());
    //carro[j].setPriority(1); // prioridade da thread

    for (int i = 0; i < N; i++) {
      carro[i].start();
    }
    System.out.println("Carro com maior prioridade: " + carro[j].getName());
  }
}

class Janela extends Frame {
  public Janela(String titulo) {
    super(titulo);
    setSize(800, 600);
    addWindowListener(new Terminator());
  }

  class Terminator extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      System.exit(0);
    }
  }
}

class Carro extends Thread {
  private int id; // identificador da thread
  private static int numCarros = 0; // nr. de threads já criadas
  private Panel canvas; // cria a parte visual dos carros
  private int pos = 0;
  private Color cor;
  private static int N = 1000; // nr. de iterações

  public Carro(Panel p, Color c) {
    id = numCarros++;
    canvas = p;
    cor = c;
  }

  public void desenharCarro() {
    Graphics g = canvas.getGraphics();
    Dimension d = canvas.getSize();
    int larg = d.height;
    int x = pos * d.width / N;

    g.setColor(cor);
    g.fillOval(x, larg / 2 - 10, 40, 20);
    g.setColor(Color.white);
    g.drawString(String.valueOf(id), x + 20, larg / 2 + 6);
    g.setColor(Color.black);

    g.fillOval(x + 10, larg / 2 - 10, 6, 4);
    g.fillOval(x + 10, larg / 2 + 6, 6, 4);
    g.fillOval(x + 24, larg / 2 - 10, 6, 4);
    g.fillOval(x + 24, larg / 2 + 6, 6, 4);
  }

  public void apagarCarro() {
    Graphics g = canvas.getGraphics();
    Dimension d = canvas.getSize();
    Color fundo = canvas.getBackground();
    int larg = d.height;
    int x = pos * d.width / N;

    g.setColor(fundo);
    g.fillOval(x, larg / 2 - 20, 6, 40);
    g.setColor(Color.black);
    g.drawLine(x, larg / 2, d.width, larg / 2);
  }

  public void mover() {
    apagarCarro();
    pos++;
    desenharCarro();
  }

  // criar o ambiente das threads
  public void run(){
    desenharCarro();

    while(pos < N){
      Thread t = Thread.currentThread(); // pega a thread atual
      try{
        Thread.sleep(1);
        if(Thread.currentThread().getName().equals(t.getName())){
          for(int i = 1; i < 50; i++){
            mover();
          }
        }else{
          mover();
        }        
      }catch(InterruptedException e){
      }
    }
    System.out.println("Chegada: " + id);
  }
}