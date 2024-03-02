import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

class CalculatorApp extends JFrame {

    private JTextField num1Field, num2Field, resultField;
    private JButton addButton, diffButton;

    public CalculatorApp() {
        setTitle("Simple Calculator - " + getCurrentDate());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        // Labels and TextFields for user input
        add(new JLabel("Enter Number 1:"));
        num1Field = new JTextField();
        add(num1Field);

        add(new JLabel("Enter Number 2:"));
        num2Field = new JTextField();
        add(num2Field);

        // Buttons for operations
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAddition();
            }
        });
        add(addButton);

        diffButton = new JButton("Find Difference");
        diffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDifference();
            }
        });
        add(diffButton);

        // Label and TextField to display the result
        add(new JLabel("Result:"));
        resultField = new JTextField();
        resultField.setEditable(false);
        add(resultField);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void performAddition() {
        try {
            double num1 = Double.parseDouble(num1Field.getText());
            double num2 = Double.parseDouble(num2Field.getText());
            double result = num1 + num2;
            resultField.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            resultField.setText("Invalid input");
        }
    }

    private void performDifference() {
        try {
            double num1 = Double.parseDouble(num1Field.getText());
            double num2 = Double.parseDouble(num2Field.getText());
            double result = Math.abs(num1 - num2);
            resultField.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            resultField.setText("Invalid input");
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }


}
class GUI{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculatorApp();
            }
        });
    }
}