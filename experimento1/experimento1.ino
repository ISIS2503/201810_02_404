#include <Keypad.h>

//---------------------------------------------------------------
// Constantes del programa
//---------------------------------------------------------------

/**
 * Número de filas del teclado
 */
const byte rows = 4;

/**
 * Número de columnas del teclado
 */
const byte cols = 4;

/**
 * Matriz de caracteres del teclado
 */
char keys[rows][cols] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};

/**
 * Pin rojo del LED RGB
 */
const int R = 13;
/**
 * Pin verde del LED RGB
 */
const int G = 12;
/**
 * Pin azul del LED RGB
 */
const int B = 11;
/**
 * Pin buzzer
 */
const int buzzer = 26;
/**
 * Pin LED del Infrarojo
 */
const int led = 25;
/**
 * Pin LED del Infrarojo
 */
const int ledBateria = 22;
/**
 * Pin del infarojo
 */
const int ir = 23;
/**
 * Pin del pulsador
 */
const int boton = 24;
/**
 * Pin batería
 */
const int bateria = 0;
/**
 * Pines de las columnas
 */
byte colPins[cols] = {5, 4, 3, 2}; //connect to the row pinouts of the keypad
/**
 * Pines de las filas
 */
byte rowPins[rows] = {9, 8, 7, 6}; //connect to the column pinouts of the keypad
/**
 * Keypad
 */
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, rows, cols );

/**
 * Tiempo entre HEALTCHECK
 */
const int healtCheckTime = 100; // In milliseconds.
/**
 * Máximo tiempo de la puerta abierta
 */
const int maxOpenedTime = 5000; // En milis
/**
 * Máximo tiempo de espera entre sonido de batería baja
 */
const int maxLowBattery = 5000; // En milis
/**
 * Número de intentos antes de alerta.
 */
const int INTENTOS = 3;
/**
 * Contraseñas
 */
char passwords[4][4] = {{'1', '2', '3', '4'}, {'1', '2', '3', '5'}, {'1', '2', '3', '6'}};
/**
 * Valor mínimo de batería
 */
const float minBateria = 1.2;
int i = 0;
char password[4] = {'\0', '\0', '\0', '\0'};

unsigned long mSHC;
unsigned long mSOT;
unsigned long mSLB;
boolean isOpen = false;
boolean block = false;
boolean lowBattery = false;
int id = 555;


int tries = 0;

void setup() {
  Serial.begin(9600);
  mSHC = millis();
  mSOT = millis();
  mSLB = millis();
  pinMode(R, OUTPUT);
  pinMode(G, OUTPUT);
  pinMode(B, OUTPUT);
  pinMode(led, OUTPUT);
  pinMode(ledBateria, OUTPUT);
  pinMode(buzzer, OUTPUT);
  pinMode(boton, INPUT);
  pinMode(ir, INPUT);
  analogWrite(R, 255);
  analogWrite(G, 255);
  analogWrite(B, 0);
}

void loop() {
  // Envía el HEALTCHECK
  if (millis() > mSHC + healtCheckTime) {
    //Serial.print(mensaje(-1));
    mSHC = millis();
  }
  
  float voltajeBateria = 0.0048*analogRead(bateria);
  if (voltajeBateria <= minBateria) {
    if (!lowBattery)
      mSLB = -maxLowBattery;
    lowBattery = true;
    digitalWrite(ledBateria, HIGH);
  } else {
    digitalWrite(ledBateria, LOW);
    lowBattery = false;
    noTone(buzzer);
  }
  
  if (lowBattery && millis() > mSLB + maxLowBattery) {
    Serial.println(mensaje(3));
    tone(buzzer, 370, 2000);
    mSLB = millis();
  }
  
  // Envía alerta de puerta abierta
  if (isOpen && millis() > maxOpenedTime + mSOT) {
    Serial.println(mensaje(0));
    analogWrite(R, 0);
    analogWrite(G, 255);
    analogWrite(B, 255);
    block = true;
    digitalWrite(buzzer, HIGH);
  } else if (!isOpen) {
    mSOT = millis();
  }

  // Enciende el LED RGB si está abierto
  if (isOpen && !block) {
    analogWrite(R, 255);
    analogWrite(G, 0);
    analogWrite(B, 255);
    digitalWrite(buzzer, LOW);
  } else if (!block) {
    analogWrite(R, 255);
    analogWrite(G, 255);
    analogWrite(B, 0);
    digitalWrite(buzzer, LOW);
  }
  // Enciende el led si hay presencia. 
  if (digitalRead(ir) && isOpen) {
    Serial.println(mensaje(2));
    digitalWrite(led, HIGH);
  }else{
        digitalWrite(led, LOW);
  }

  if (digitalRead(boton) == HIGH && !isOpen) {
    block = false;
    isOpen = true;
    tries = 0;
    i = 0;
  } else if (digitalRead(boton) == LOW && isOpen && password[0] != '-') {
    isOpen = false;
    block = false;
    tries = 0;
    i = 0;
    mSOT = millis();
  } else {
    char key = keypad.getKey();
    if (key != NO_KEY) {
      // Cerrar la puerta
      if (key == '*' && isOpen) {
        isOpen = false;
        tries = 0;
        block = false;
        i = 0;
        password[0] = '\0';
        password[1] = '\0';
        password[2] = '\0';
        password[3] = '\0';
      } else if (key == '#') {
        i = 0;
      }else if (!isOpen) {
        password[i++] = key;
        if (i == 4) {
          if (existsPassword()) {
            block = false;
            isOpen = true;
            password[0] = '-';
            tries = 0;
            mSOT = millis();
          } else {
            digitalWrite(buzzer, HIGH);
            if (++tries >= INTENTOS) {
              Serial.println(mensaje(1));
              analogWrite(R, 0);
              analogWrite(G, 255);
              analogWrite(B, 255);
              delay(30000);
              tries = 0;
            } else {
              analogWrite(R, 0);
              analogWrite(G, 255);
              analogWrite(B, 255);
              delay(1000);
            }
          }
          i = 0;
        }
      }
    }
  }
}

/**
* Indica si existe una contraseña
*/
boolean existsPassword() {
  boolean existsPassword = false;
  for (int k = 0; k < 4 && !existsPassword; k++) {
    for (int j = 0; j < 4; j++) {
      if (passwords[k][j] != password[j]) {
        existsPassword = false;
        break;
      }
      existsPassword = true;
    }
  }
  return existsPassword;
}

/**
* Prepara una trama de datos 
**/
String mensaje(int tipo){
  String temp = "ALERTA::";
  String temp2 = temp + tipo;
  temp = temp2 + "::";
  temp2 = temp + id;
  return temp2;
}
