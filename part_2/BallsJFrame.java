//By 202171103Wang
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
public class BallsJFrame extends JFrame implements ActionListener,Runnable
{
	private BallsCanvas canvas;
	private JButton b1,b2,b3,b4;
	private JTextField t[];
	private JTextField text_filename;//�ļ�����
	private String filename;
	private JButton b0,b5;
	private Color color;
	private Ballbuffer buffer2;    //�����߳�
	private ArrayList<Ball> balls;
	Thread thread1,thread2;
	public BallsJFrame(String filename,Ballbuffer buffer1,Ballbuffer buffer2)               //������
	{
		super("������");
		this.setBounds(300,200,800,300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.buffer2=buffer2;
		this.filename=filename;
		this.canvas=new BallsCanvas(buffer1);
		this.readFrom(this.filename);
		
		this.balls=new ArrayList<Ball>(this.canvas.balls);
		this.balls=this.canvas.balls;
		thread2=new Thread(this);
		thread2.start();
		
		this.getContentPane().add(this.canvas);
		thread1=new Thread(this.canvas);
		
		JToolBar panel=new JToolBar();
		this.getContentPane().add(panel,"North");
		
		panel.add(new JLabel("�ļ���"));
		panel.add(text_filename=new JTextField(filename,20));
		text_filename.setEditable(false);
		
		panel.add(b0=new JButton("����"));
		b0.addActionListener(this);
		b1=new JButton("ѡ����ɫ");
		b1.addActionListener(this);
		panel.add(b1);
		
		b2=new JButton("ȷ��");
		b2.addActionListener(this);
		String str[]={"ѡ�����","ѡ���С","ѡ���ٶ�"};
		this.t=new JTextField[str.length];
		for(int i=0;i<this.t.length;i++)
		 {
			panel.add(new JLabel(str[i]));		    
			panel.add(this.t[i]=new JTextField(10));
		 }
		panel.add(b2);
		
		b3=new JButton("��ʼ");
		b4=new JButton("��ͣ");
		b5=new JButton("���");
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		panel.add(b3);
		panel.add(b4);
		panel.add(b5);
		
		this.setVisible(true);
	}
    public void actionPerformed(ActionEvent ev) 
	{
		if(ev.getActionCommand().equals("ѡ����ɫ"))
		this.color=JColorChooser.showDialog(this,"ѡ����ɫ",Color.white);
		if(ev.getActionCommand().equals("ȷ��"))
		{ 	try
	    	{
				int x=Integer.parseInt(t[0].getText());
				int y=Integer.parseInt(t[1].getText());
				int v=Integer.parseInt(t[2].getText());
				if(x>0&&y>0&&v>0)
				 this.canvas.addballs(this.color,x,100,y,v);
				else
				 JOptionPane.showMessageDialog(null,"���������0�����֣�");
	    	}
	    	catch(NumberFormatException nefx)
	    	{
		    	JOptionPane.showMessageDialog(null, "��������ȷ���֣�");
	    	}
		}
		if(ev.getActionCommand().equals("��ʼ"))
		{
			thread1=new Thread(this.canvas);
			thread1.start();
			if(this.canvas.balls.isEmpty())
				{
				   JOptionPane.showMessageDialog(null,"������û��������ӣ�");
				   this.b4.setEnabled(true);
				   this.b3.setEnabled(true);
				}
			else
			{
			   this.b4.setEnabled(true);
			   this.b3.setEnabled(false);
			}
		}
		if(ev.getActionCommand().equals("��ͣ"))
		{
			thread1.interrupt();
			if(this.canvas.balls.isEmpty())
			{
			   JOptionPane.showMessageDialog(null,"������û��������ӣ�");
			   this.b4.setEnabled(true);
			   this.b3.setEnabled(true);
			}
			else
			{
			  this.b3.setEnabled(true);
			  this.b4.setEnabled(false);	
			}
		}
		if(ev.getActionCommand().equals("����"))
		{
			
			this.writeTo(this.filename);
		}
		if(ev.getActionCommand().equals("���"))
		{
			this.canvas.balls.clear();
			JOptionPane.showMessageDialog(null,"���ȫ����");
		}
	}
    public<T> void readFrom(String filename)
    {
    	try
    	{
    		FileInputStream fin=new FileInputStream(filename);
    		ObjectInputStream objin=new ObjectInputStream(fin);
    		while(true)
    			try
    		    {
    				this.canvas.balls.add((Ball)objin.readObject());
    		    }catch(EOFException eofx) {  break;}
    		objin.close();
    		fin.close();
    	}
    	catch(Exception e) {}
    }
    public void writeTo(String filename)
    {
    	try
    	{
    		FileOutputStream fout=new FileOutputStream(filename);
    		ObjectOutputStream objout=new ObjectOutputStream(fout);
    		for(int i=0;i<this.canvas.balls.size();i++)
    		{
    			  objout.writeObject(this.canvas.balls.get(i));
    		}
    		objout.close();
    		fout.close();
    	}catch(Exception e) {}
    }
    public void run()
    {
    	while(true)
		{
		   for(int i=0;i<2;i++)
			 {
			    this.canvas.addBall(buffer2.get());
			 }
		   thread1=new Thread(this.canvas);
		   thread1.start();
		   try{Thread.sleep(100);}
		   catch(InterruptedException e)
		   {
				  break;
		   }  
		}
    }
	public static void main(String ags[])
	{
		Ballbuffer buffer1=new Ballbuffer();
		Ballbuffer buffer2=new Ballbuffer();
		BallsJFrame b=new BallsJFrame("Ball.obj",buffer1,buffer2);
		receiveJFrame r=new receiveJFrame(buffer1,buffer2);
		
	}
}

