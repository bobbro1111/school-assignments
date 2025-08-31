import java.awt.Font;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderDuration extends SliderPanel {

    public SliderDuration(int row, int column) {
        super(row, column);

        //Uses setters rather than remaking slider
        title.setText("Duration (days)");
        slider.setMinimum(1);
        slider.setMaximum(10);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
		slider.setPaintLabels(true);

        //https://docs.oracle.com/javase/7/docs/api/javax/swing/JSlider.html#createStandardLabels%28int%29
        slider.setLabelTable(slider.createStandardLabels(1));
    
        slider.addChangeListener(durationListener);

    }

    ChangeListener durationListener = new ChangeListener() {
	
		@Override
		public void stateChanged(ChangeEvent ce) {

			MessageBoard.update(Message.DURATION, slider.getValue());
            valueLabel.setText(Integer.toString(slider.getValue()));

			
			// need to update the "observers" of this slider
		}
	}; // end sliderListener
}