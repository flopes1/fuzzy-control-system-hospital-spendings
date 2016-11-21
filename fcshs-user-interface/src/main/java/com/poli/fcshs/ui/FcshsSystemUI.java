package com.poli.fcshs.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.poli.fcshs.FcshsSystemFacade;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class FcshsSystemUI
{

	private FcshsSystemFacade systemFacade;
	private JFrame frame;
	private AboutFrame aboutFrame = new AboutFrame();
	private JMenuBar menuBar;
	private JPanel imagesPanel;
	private JPanel mainPanel;
	private JTextField hospitalName;
	private JTextField year;
	private JCheckBox allYears;
	private JLabel results;

	public FcshsSystemUI()
	{
		this.systemFacade = FcshsSystemFacade.getInstance();
		initialize();
	}

	private void initialize()
	{
		this.setGeneralSettings();
		this.setDesignSettings();
		this.setMenuSettings();
	}

	private void setGeneralSettings()
	{
		this.frame = new JFrame();
		this.frame.getContentPane().setFont(new Font("Calibri", Font.PLAIN, 12));
		this.frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.frame.getContentPane().setLayout(null);
		this.frame.setBounds(100, 100, 950, 500);
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setMenuSettings()
	{
		this.menuBar = new JMenuBar();
		this.frame.setJMenuBar(menuBar);

		JMenu mnSettings = new JMenu("Options");
		mnSettings.setFont(new Font("Calibri", Font.PLAIN, 12));
		this.menuBar.add(mnSettings);

		JMenuItem mntmSystem = new JMenuItem("Settings");
		mntmSystem.setFont(new Font("Calibri", Font.PLAIN, 12));
		mnSettings.add(mntmSystem);

		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Calibri", Font.PLAIN, 12));
		this.menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				aboutFrame.openAboutWindow();
			}
		});
		mntmAbout.setFont(new Font("Calibri", Font.PLAIN, 12));
		mnHelp.add(mntmAbout);
	}

	private void setDesignSettings()
	{
		this.frame.setTitle(FcshsUIConstants.APLICATION_TITLE);
		URL url = this.getClass().getClassLoader().getResource(FcshsUIConstants.APLICATION_ICON);
		Image titleIcon = Toolkit.getDefaultToolkit().getImage(url);
		this.frame.setIconImage(titleIcon);
		this.frame.setBackground(Color.WHITE);

		this.imagesPanel = new JPanel();
		this.imagesPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		this.imagesPanel.setBackground(Color.WHITE);
		this.imagesPanel.setBounds(10, 304, 914, 125);
		this.frame.getContentPane().add(imagesPanel);
		this.imagesPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		url = this.getClass().getClassLoader().getResource("upe.png");
		Image upeImage = Toolkit.getDefaultToolkit().getImage(url);
		lblNewLabel.setIcon(new ImageIcon(upeImage));
		lblNewLabel.setBounds(10, 11, 217, 103);
		imagesPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		url = this.getClass().getClassLoader().getResource("poli.jpg");
		upeImage = Toolkit.getDefaultToolkit().getImage(url);
		lblNewLabel_1.setIcon(new ImageIcon(upeImage));
		lblNewLabel_1.setBounds(350, 11, 209, 103);
		imagesPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("");
		url = this.getClass().getClassLoader().getResource("ecomp.jpg");
		upeImage = Toolkit.getDefaultToolkit().getImage(url);
		lblNewLabel_2.setIcon(new ImageIcon(upeImage));
		lblNewLabel_2.setBounds(687, 11, 217, 103);
		imagesPanel.add(lblNewLabel_2);

		mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(10, 11, 914, 282);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		JLabel lblNomeDoHospital = new JLabel("Nome do Hospital:");
		lblNomeDoHospital.setFont(new Font("Calibri", Font.BOLD, 14));
		lblNomeDoHospital.setBackground(Color.WHITE);
		lblNomeDoHospital.setBounds(10, 11, 120, 28);
		mainPanel.add(lblNomeDoHospital);

		hospitalName = new JTextField();
		hospitalName.setFont(new Font("Calibri", Font.PLAIN, 14));
		hospitalName.setBounds(140, 15, 164, 20);
		mainPanel.add(hospitalName);
		hospitalName.setColumns(10);

		JLabel lblAnoDeAnlise = new JLabel("Ano de An\u00E1lise:");
		lblAnoDeAnlise.setFont(new Font("Calibri", Font.BOLD, 14));
		lblAnoDeAnlise.setBounds(325, 18, 120, 14);
		mainPanel.add(lblAnoDeAnlise);

		year = new JTextField();
		year.setFont(new Font("Calibri", Font.PLAIN, 14));
		year.setBounds(434, 15, 90, 20);
		mainPanel.add(year);
		year.setColumns(10);

		allYears = new JCheckBox("Analisar toda base");
		allYears.setBackground(Color.WHITE);
		allYears.setFont(new Font("Calibri", Font.BOLD, 14));
		allYears.setBounds(553, 14, 135, 23);
		mainPanel.add(allYears);

		JButton btnNewButton = new JButton("Analisar");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				results.setText("");
				String name = hospitalName.getText();
				String ano = year.getText();
				boolean allYearsCheck = allYears.isSelected();

				if (name == null || name.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Digite o nome do hospital");
				}
				if (!allYearsCheck)
				{
					if (ano == null || ano.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Digite o ano para analise");
					}
				}

				String analisysResult = "";

				if (allYearsCheck)
					analisysResult = systemFacade.getSystemController().getSystemAnalysisByHospital(name);
				else
					analisysResult = systemFacade.getSystemController().getSystemAnalysisByHospital(name, ano);

				results.setText(analisysResult);

			}
		});
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setFont(new Font("Calibri", Font.BOLD, 14));
		btnNewButton.setBounds(739, 14, 89, 23);
		mainPanel.add(btnNewButton);

		JLabel lblResultados = new JLabel("Resultados:");
		lblResultados.setFont(new Font("Calibri", Font.BOLD, 14));
		lblResultados.setBackground(Color.WHITE);
		lblResultados.setBounds(10, 85, 79, 14);
		mainPanel.add(lblResultados);

		results = new JLabel("");
		results.setHorizontalAlignment(SwingConstants.LEFT);
		results.setVerticalAlignment(SwingConstants.TOP);
		results.setFont(new Font("Calibri", Font.PLAIN, 14));
		results.setBounds(10, 110, 894, 161);
		mainPanel.add(results);

	}

	public void startUserAplication()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					FcshsSystemUI window = new FcshsSystemUI();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
