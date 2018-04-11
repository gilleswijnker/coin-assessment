FROM mongo

COPY address_records.json /address_records.json
CMD mongoimport --host mongo --db coin --collection assessment --jsonArray --drop --file /address_records.json
