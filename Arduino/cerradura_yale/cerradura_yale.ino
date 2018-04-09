#include <Keypad.h>
#include <EEPROM.h>

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
const int ir = 28;
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
 * Valor mínimo de batería
 */
const float minBateria = 1.2;
int i = 0;
String password = "";

unsigned long mSHC;
unsigned long mSOT;
unsigned long mSLB;
boolean isOpen = false;
boolean block = false;
boolean lowBattery = false;
int id = 555;
String inputString = "";
String commandString = "";
int indexPassword = -1;
int newPassword = 0;

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
  receiveData();
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
    //Serial.println(mensaje(3));
    //tone(buzzer, 370, 2000);
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
  } else if (digitalRead(boton) == LOW && isOpen && !password.equals("-")) {
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
        password = "";
      } else if (key == '#') {
        i = 0;
        password = "";
      }else if (!isOpen) {
        password += key;
        i++;
        if (i == 4) {
          if (compareKey(password)) {
            block = false;
            isOpen = true;
            password = "-";
            tries = 0;
            mSOT = millis();
          } else {
            password = "";
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
 * Recibe datos del módulo wi-fi
 */
void receiveData() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    if (inChar == '-') {
      switch (newPassword) {
        case 0:
          commandString = inputString;
          break;
        case 1:
          indexPassword = inputString.toInt();
          break;
        case 2:
          newPassword = inputString.toInt();
          break;
      }
      processData();
      resetValues();
    } else if (inChar == ';') {
      switch (newPassword) {
        case 0:
          commandString = inputString;
          break;
        case 1:
          indexPassword = inputString.toInt();
          break;
      }
      inputString = "";
      newPassword++;
    } else {
      // add it to the inputString:
      inputString += inChar;
    }
   
  }
}

void resetValues() {
  inputString = "";
  commandString = "";
  indexPassword = -1;
  newPassword = 0;
}

/**
 * Procesa los datos recibidos del módulo wi-fi
 */
void processData() {
  if (commandString.equals("ADD_PASSWORD")) {
    addPassword(newPassword, indexPassword);
  } else if (commandString.equals("CHANGE_PASSWORD")) {
    updatePassword(newPassword, indexPassword);
  } else if (commandString.equals("DELETE_PASSWORD")) {
    deletePassword(indexPassword);
  } else if (commandString.equals("DELETE_ALL_PASSWORDS")) {
    deleteAllPasswords();
  }
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

//----------------------------------------------
// Método de las contaseñas.
//-----------------------------------------------

// Method that compares a key with stored keys
boolean compareKey(String key) {
  int acc = 3;
  int codif, arg0, arg1; 
  for(int i=0; i<3; i++) {
    codif = EEPROM.read(i);
    while(codif!=0) {
      if(codif%2==1) {
        arg0 = EEPROM.read(acc);
        arg1 = EEPROM.read(acc+1)*256;
        arg1+= arg0;
        if(String(arg1)==key) {
          return true;
        }
      }
      acc+=2;
      codif>>=1;
    }
    acc=(i+1)*16+3;
  }
  return false;
}

//Method that adds a password in the specified index
void addPassword(int val, int index) {
  byte arg0 = val%256;
  byte arg1 = val/256;
  EEPROM.write((index*2)+3,arg0);
  EEPROM.write((index*2)+4,arg1);
  byte i = 1;
  byte location = index/8;
  byte position = index%8;
  i<<=position;
  byte j = EEPROM.read(location);
  j |= i;
  EEPROM.write(location,j);
}

//Method that updates a password in the specified index
void updatePassword(int val, int index) {
  byte arg0 = val%256;
  byte arg1 = val/256;
  EEPROM.write((index*2)+3,arg0);
  EEPROM.write((index*2)+4,arg1);
}

//Method that deletes a password in the specified index
void deletePassword(int index) {
  byte i = 1;
  byte location = index/8;
  byte position = index%8;
  i<<=position;
  byte j = EEPROM.read(location);
  j ^= i;
  EEPROM.write(location,j);
}

//Method that deletes all passwords
void deleteAllPasswords() {
  //Password reference to inactive
  EEPROM.write(0,0);
  EEPROM.write(1,0);
  EEPROM.write(2,0);
}

