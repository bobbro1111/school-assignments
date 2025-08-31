import java.awt.Font;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderMorbidity extends SliderPanel {

    public SliderMorbidity(int row, int column) {
        super(row, column);

        //Uses setters rather than remaking slider
        title.setText("Morbidity (Percentage)");
        slider.addChangeListener(morbidityListener);
        slider.setValue(0);
        MessageBoard.update(Message.MORBIDITY, slider.getValue()); //NEEDS THIS OR WILL CRASH WITH DEFAULT SETTINGS
        valueLabel.setText("0");
        slider.repaint();
    }

    ChangeListener morbidityListener = new ChangeListener() {
	
		@Override
		public void stateChanged(ChangeEvent ce) {

			MessageBoard.update(Message.MORBIDITY, slider.getValue());
            valueLabel.setText(Integer.toString(slider.getValue()));

			
			// need to update the "observers" of this slider
		}
	}; // end sliderListener
}