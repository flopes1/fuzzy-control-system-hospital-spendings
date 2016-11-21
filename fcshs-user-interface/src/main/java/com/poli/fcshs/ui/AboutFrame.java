package com.poli.fcshs.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class AboutFrame extends JFrame
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public void openAboutWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					AboutFrame frame = new AboutFrame();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AboutFrame()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Universidade de Pernambuco");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel.setBounds(54, 30, 314, 17);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Escola Polit\u00E9cnica de Pernambuco");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(123, 58, 180, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Projeto de Conclusão da Disciplina de Inteligencia Artificial");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setBounds(54, 83, 326, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblDesenvolvidoPor = new JLabel("Desenvolvido por:");
		lblDesenvolvidoPor.setBounds(164, 108, 114, 14);
		lblDesenvolvidoPor.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblDesenvolvidoPor.setBackground(Color.WHITE);
		contentPane.add(lblDesenvolvidoPor);
		
		JLabel erik = new JLabel("Erik Vinicius - evps@ecomp.poli.br");
		erik.setBounds(123, 133, 206, 14);
		erik.setFont(new Font("Calibri", Font.PLAIN, 14));
		erik.setBackground(Color.WHITE);
		contentPane.add(erik);
		
		JLabel filipe = new JLabel("Filipe Lopes - flm@ecomp.poli.br");
		filipe.setBounds(133, 158, 191, 14);
		filipe.setFont(new Font("Calibri", Font.PLAIN, 14));
		filipe.setBackground(Color.WHITE);
		contentPane.add(filipe);
		
		JLabel marcos = new JLabel("Marcos Victor - mvmp@ecomp.poli.br");
		marcos.setBounds(123, 185, 225, 14);
		marcos.setFont(new Font("Calibri", Font.PLAIN, 14));
		marcos.setBackground(Color.WHITE);
		contentPane.add(marcos);
		
		JLabel lblRecife = new JLabel("Recife - 2016");
		lblRecife.setBackground(Color.WHITE);
		lblRecife.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblRecife.setBounds(178, 236, 74, 14);
		contentPane.add(lblRecife);
		
		setTitle("About");
		URL url = this.getClass().getClassLoader().getResource(FcshsUIConstants.APLICATION_ICON);
		Image titleIcon = Toolkit.getDefaultToolkit().getImage(url);
		setIconImage(titleIcon);
		setBackground(Color.WHITE);
	}

}
