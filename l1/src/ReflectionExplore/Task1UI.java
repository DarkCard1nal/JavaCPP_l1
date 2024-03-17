package ReflectionExplore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Task1UI extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField inputField;
	private JTextArea outputArea;

	public Task1UI() {
		setTitle("Аналізатор класу");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(400, 300));

		JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
		
		// Лейбл з текстом
		JLabel label = new JLabel(" Введіть повне ім'я класу: ");
		inputPanel.add(label, BorderLayout.WEST);

		// Поле для вводу тексту
		inputField = new JTextField();
		inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));
		inputPanel.add(inputField, BorderLayout.CENTER);
		
		JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.X_AXIS));

		// Поле для виводу тексту з рамкою
		outputArea = new JTextArea();
		outputArea.setBorder(BorderFactory.createEtchedBorder());
		outputArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));
		outputArea.setEditable(false);
		outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

		// Панель для кнопок
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton analyzeButton = new JButton("Аналіз");
		JButton clearButton = new JButton("Очистити");
		JButton exitButton = new JButton("Завершити");

		analyzeButton.addActionListener(this);
		clearButton.addActionListener(this);
		exitButton.addActionListener(this);

		buttonPanel.add(analyzeButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(exitButton);

		add(inputPanel, BorderLayout.NORTH);
		add(outputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "Аналіз":
			try {
				outputArea.setText(Task1.getClassDescription(inputField.getText()));
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				outputArea.setText(e1.toString());
			}
			break;
		case "Очистити":
			inputField.setText("");
			outputArea.setText("");
			break;
		case "Завершити":
			System.exit(0);
			break;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Task1UI app = new Task1UI();
			app.setVisible(true);
		});
	}

}
