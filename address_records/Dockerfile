FROM mongo

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY address_records.json /address_records.json

CMD mongoimport --host mongodb --db coin --collection assessment --jsonArray --drop --file /address_records.json
