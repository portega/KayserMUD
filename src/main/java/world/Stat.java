package world;

import lombok.Data;

@Data
public class Stat {
  private int value, max;

  public boolean checkDelta(int delta) {
    int newValue = getValue() + delta;
    return (0 <= newValue && newValue <= getMax());
  }

  public boolean checkValue(int newValue) {
    return (0 <= newValue && newValue <= getMax());
  }

  public int add(int delta) {
    if (checkValue(delta)) {
      setValue(getValue() + delta);
    }

    return getValue();
  }

  public void setMax(int max) {
    this.max = max;
    this.value = max;
  }
}
