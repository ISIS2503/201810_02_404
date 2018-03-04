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
 * Pin LED del Infrarojo
 */
const int led = 0;
/**
 * Pin del infarojo
 */
const int ir = 1;
/**
 * Pin del pulsador
 */
const int boton = 10;
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
const int maxOpenedTime = 1000; // En milis
/**
 * Número de intentos antes de alerta.
 */
const int INTENTOS = 3;
/**
 * Contraseñas
 */
char passwords[4][4] = {{'1', '2', '3', '4'}, {'1', '2', '3', '5'}, {'1', '2', '3', '6'}};

int i = 0;
char password[4] = {'\0', '\0', '\0', '\0'};

int mSHC;
int mSOT;
boolean isOpen = false;
int tries = 0;

void setup() {
  Serial.begin(9600);
  Serial.println("HEALTCHECK");
  mSHC = millis();
  mSOT = millis();
  pinMode(R, OUTPUT);
  pinMode(G, OUTPUT);
  pinMode(B, OUTPUT);
  pinMode(led, OUTPUT);
  pinMode(boton, INPUT);
  pinMode(ir, INPUT);
  analogWrite(R, 255);
  analogWrite(G, 255);
  analogWrite(B, 0);
}

void loop() {
  // Envía el HEALTCHECK
  if (millis() > mSHC + healtCheckTime) {
    //Serial.println("HEALTCHECK");
    mSHC = millis();
  }
  // Envía alerta de puerta abierta
  if (isOpen && millis() > maxOpenedTime + mSOT) {
    //Serial.println("ALERTA:0");
  } else if (!isOpen) {
    mSOT = millis();
  }

  // Enciende el LED RGB si está abierto
  if (isOpen) {
    analogWrite(R, 255);
    analogWrite(G, 0);
    analogWrite(B, 255);
  } else {
    analogWrite(R, 255);
    analogWrite(G, 255);
    analogWrite(B, 0);
  }
  // Enciende el led si hay presencia. 
  if (digitalRead(ir)) {
    //Serial.println("ALERTA:1");
    digitalWrite(led, HIGH);
  }
  
  char key = keypad.getKey();
  if (key != NO_KEY) {
    // Cerrar la puerta
    if (key == 'C') {
      isOpen = false;
    } else if (!isOpen) {
      if (i == 4) {
        if (existsPassword()) {
          i = 0;
          isOpen = true;
          mSOT = millis();
        } else {
          if (++tries >= INTENTOS) {
            Serial.println("ALERTA:1");
          }
          i = 0;
          analogWrite(R, 0);
          analogWrite(G, 255);
          analogWrite(B, 255);
        }
      } else {
        password[i++] = key;
      }
      i++;
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

