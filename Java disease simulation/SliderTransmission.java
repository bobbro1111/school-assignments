import java.awt.Font;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderTransmission extends SliderPanel {

    public SliderTransmission(int row, int column) {
        super(row, column);
        
        // title of the slider
        title.setText("Transmission Probability");

        // creating a new slider
        remove(slider);
        // Set up the slider
		slider = new JSlider(JSlider.HORIZONTAL,0,100, MessageBoard.transmissionProbability());
		slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setFont(new Font("Verdana", Font.PLAIN, 12));
        add(slider);
	
		
		// position is relative to the origin of this panel
		slider.setBounds(
			Layout.SLIDER_X, Layout.SLIDER_Y,
			Layout.SLIDER_W, Layout.SLIDER_H
		);

        

        // event listener to update 
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                MessageBoard.update(Message.TRANSMISSION, slider.getValue());
                valueLabel.setText(String.valueOf(slider.getValue()));
            }
        });
    }
}

