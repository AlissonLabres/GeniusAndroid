package Classes;

import android.widget.Button;

public class ButtonColor {

    private Button button;
    private int color;

    public ButtonColor(Button button, int color) {
        this.button = button;
        this.color = color;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
