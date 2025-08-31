const int RED = 13;
const int RED_BUTTON = 8;
const int GREEN = 12;
const int GREEN_BUTTON = 7;
int redState = 0;
int greenState = 0;
unsigned long prevMillis = 0;
unsigned long prevMillis2 = 0;
unsigned long currMillis = 0;
unsigned long interval = 500; //1HZ is equal to 1000 milliseconds
unsigned long interval2 = 50;
boolean canBlinkRed = false;
boolean canBlinkGreen = false;
boolean prevGreenState = false;
boolean currGreenState = false;

void setup() {
  pinMode(RED, OUTPUT);
  pinMode(RED_BUTTON, INPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(GREEN_BUTTON, INPUT);

}

void loop() {
  currMillis = millis();

  //Detect red button press
  if (HIGH == digitalRead(RED_BUTTON)) {
    canBlinkRed = true;
  }

  //Red blinking
  if ((currMillis - prevMillis >= interval) && canBlinkRed) {
    prevMillis = currMillis;
    if (redState == 0) {
      redState = 1;
    } else {
      redState = 0;
    }
  }

  //Detect green button press
  if (LOW == digitalRead(GREEN_BUTTON)) {
    prevGreenState = false;
  }

  currGreenState = digitalRead(GREEN_BUTTON);
  if (currGreenState && !prevGreenState) {
    prevGreenState = true;
    canBlinkGreen = true;
    interval2 += 50;
    delay(50); //This should not be neccessary; This is "Debouncing delay" because of wokwi
  }

  if ((currMillis - prevMillis2 >= interval2) && canBlinkGreen) {
    prevMillis2 = currMillis;
    if (greenState == 0) {
      greenState = 1;
    } else {
      greenState = 0;
    }
  }

  digitalWrite(RED, redState);
  digitalWrite(GREEN, greenState);


}