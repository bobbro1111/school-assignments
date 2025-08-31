const int RED = 13;
const int RED_BUTTON = 8;
int redState = 0;
unsigned long prevMillis = 0;
unsigned long currMillis = 0;
unsigned long interval = 500; //1HZ is equal to 1000 milliseconds
boolean canBlinkRed = false;


void setup() {
  pinMode(RED, OUTPUT);
  pinMode(RED_BUTTON, INPUT);
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
  digitalWrite(RED, redState);


}