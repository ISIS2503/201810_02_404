from kafka import KafkaProducer
from kafka.errors import KafkaError
from kafka import KafkaConsumer
import datetime
import json
tipo=-1
leer="0"

# Subscribe to a regex topic pattern
consumer = KafkaConsumer()
consumer.subscribe(pattern='propietario.apto1.alerta')
# To consume latest messages and auto-commit offsets
consumer = KafkaConsumer('propietario.apto1.alerta',
                         group_id='my-group',
                         bootstrap_servers=['192.168.0.3:8083'])
for message in consumer:
	leer=message.value
	tipo=leer["alert"]["type"]
    # message value and key are raw bytes -- decode if necessary!
    # e.g., for unicode: `message.value.decode('utf-8')`
    print ("%s:%d:%d: key=%s value=%s" % (message.topic, message.partition,
                                          message.offset, message.key,
                                          message.value))
producer = KafkaProducer(bootstrap_servers=['broker1:8083'])

# Asynchronous by default
if tipo is "0" and leer is not "0"
	future = producer.send('propietario.apto1.alerta.abierta', b'raw_bytes')
	print("ALERTA 0 = TIEMPO MAXIMO DE APERTURA SOBREPASADO \n FECHA: ",leer["sensetime"])
	leer = "0"
if tipo is "1" and leer is not "0"
	future = producer.send('propietario.apto1.alerta.permiso', b'raw_bytes')
	print("ALERTA 1 = INTENTOS DE APERTURA SOBREPASADOS \n FECHA: ",leer["sensetime"])
	leer = "0"
if tipo is "2" and leer is not "0"
	future = producer.send('propietario.apto1.alerta.sospecha', b'raw_bytes')
	print("ALERTA 2 = SENSOR DE PRESENCIA ACTIVADO \n FECHA: ",leer["sensetime"])
	leer = "0"

# Block for 'synchronous' sends
try:
    record_metadata = future.get(timeout=10)
except KafkaError:
    # Decide what to do if produce request failed...
    log.exception()
    pass
