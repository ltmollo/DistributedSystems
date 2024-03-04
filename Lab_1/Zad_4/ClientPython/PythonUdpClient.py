import socket

serverIP = "127.0.0.1"
serverPort = 9008
msg = "Ping Python Udp"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'cp1250'), (serverIP, serverPort))

data, addr = client.recvfrom(1024)
message = data.decode('cp1250')
print("Received message:", message)



