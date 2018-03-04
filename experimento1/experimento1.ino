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
const int maxOpenedTime = 30000; // En milis
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

boolean bloqueo = false;
int id = 555;
String mensaje = "";
String temp = "";


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
    bloqueo = true;
    analogWrite(R, 0);
    analogWrite(G, 255);
    analogWrite(B, 255);
    temp = "ALERTA::0::";
    mensaje = temp + id;
    Serial.println(mensaje);
  } else if (!isOpen) {
    mSOT = millis();
  }
  
  // Enciende el led si hay presencia. 
  if (digitalRead(ir) && isOpen) {
    temp = "ALERTA::2::";
    mensaje = temp + id;
    Serial.println(mensaje);
    digitalWrite(led, HIGH);
  }

  if(!bloqueo)isOpen = false;
  char key = keypad.getKey();
  if(digitalRead(boton) == 1){
    isOpen = true;
  }
  else{
  if (key != NO_KEY) {
    // Cerrar la puerta
    if (key == 'C') {
      isOpen = false;
      bloqueo = false;
      tries = 0;
    }else if (key == '#') {
      if(i>0)i--;
    }else if (!isOpen) {
        password[i++] = key;
        if (i == 4) {
          //Serial.println("Prueba");
        if (existsPassword()) {
          //Serial.println("Bien");
          i = 0;
          isOpen = true;
          mSOT = millis();
          analogWrite(R, 255);
          analogWrite(G, 0);
          analogWrite(B, 255);
          bloqueo = true;
        } else {
          //Serial.println("Mal");
          if (++tries >= INTENTOS) {
            temp = "ALERTA::0::";
            mensaje = temp + id;
            Serial.println(mensaje);
            analogWrite(R, 0);
            analogWrite(G, 255);
            analogWrite(B, 255);
            bloqueo = true;
          }else{
           analogWrite(R, 0);
           analogWrite(G, 255);
           analogWrite(B, 255);
           delay(1000); 
           analogWrite(R, 255);
           analogWrite(G, 255);
           analogWrite(B, 0);
           
          }
          i = 0;
         }
      }
    }
    }
  }

    // Enciende el LED RGB si está abierto
  if(!bloqueo){
    if (isOpen) {
      analogWrite(R, 255);
      analogWrite(G, 0);
      analogWrite(B, 255);
    }
    else{
      analogWrite(R, 255);
      analogWrite(G, 255);
      analogWrite(B, 0);
    }
  }
  else{
    //Serial.println("BLOQUEADO");
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
