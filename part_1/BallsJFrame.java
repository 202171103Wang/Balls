//By 202171103Wang
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class BallsJFrame extends JFrame implements ChangeListener,ActionListener
{
	private BallsCanvas canvas;
	private JSpinner spinner;
	private JButton b1,b2,b3,b4;
	private JTextField t1,t2,t3;
	private Color color;
	Thread thread;
	public BallsJFrame()
	{
		super("������");
		this.setBounds(300,200,600,300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.color=Color.red;
		this.canvas=new BallsCanvas(this.color);
		this.getContentPane().add(this.canvas);
		thread=new Thread(this.canvas);
		JToolBar panel=new JToolBar();
		this.getContentPane().add(panel,"North");
		panel.add(new JLabel("��ʱ"));
		SpinnerNumberModel model=new SpinnerNumberModel();
		model.setStepSize(20);
		this.spinner=new JSpinner(model);
		this.spinner.setValue(100);
		this.spinner.addChangeListener(this);
		panel.add(this.spinner);
		b1=new JButton("ѡ����ɫ");
		b1.addActionListener(this);
		panel.add(b1);
		t1=new JTextField(10);
		t2=new JTextField(10);
		t3=new JTextField(10);
		b2=new JButton("ȷ��");
		b2.addActionListener(this);
		panel.add(new JLabel("ѡ�����"));
		panel.add(t1);
		panel.add(new JLabel("ѡ���С"));
		panel.add(t2);
		panel.add(new JLabel("ѡ���ٶ�"));
		panel.add(t3);
		panel.add(b2);
		b3=new JButton("��ʼ");
		b4=new JButton("��ͣ");
		b3.addActionListener(this);
		b4.addActionListener(this);
		panel.add(b3);
		panel.add(b4);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent ev) 
	{
		if(ev.getActionCommand().equals("ѡ����ɫ"))
			this.color=JColorChooser.showDialog(this,"ѡ����ɫ",Color.white);
		if(ev.getActionCommand().equals("ȷ��"))
		{ 	try
	    	{
				int time=Integer.parseInt(""+this.spinner.getValue());
				int x=Integer.parseInt(t1.getText());
				int y=Integer.parseInt(t2.getText());
				int v=Integer.parseInt(t3.getText());
				this.canvas.addballs(this.color,x,time,y,v);
	    	}
	    	catch(NumberFormatException nefx)
	    	{
		    	JOptionPane.showMessageDialog(null, "��������ȷ���֣�");
	    	}
		}
		if(ev.getActionCommand().equals("��ʼ"))
		{
			thread=new Thread(this.canvas);
			thread.start();
			this.b4.setEnabled(true);
			this.b3.setEnabled(false);
		}
		if(ev.getActionCommand().equals("��ͣ"))
		{
			thread.interrupt();
			this.b3.setEnabled(true);
			this.b4.setEnabled(false);	
		}
	}
	public void stateChanged(ChangeEvent e)
	{
		this.canvas.setDelay(Integer.parseInt(""+this.spinner.getValue()));
	}
	public static void main(String ags[])
	{
		new BallsJFrame();
	}
}
	
