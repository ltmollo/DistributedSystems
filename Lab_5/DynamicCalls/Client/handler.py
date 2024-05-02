import subprocess

def call_method(method, args_json = None, obj = None):
    curl = "./grpcurl.exe"
    plain_text = "-plaintext"
    server = "localhost:50051"

    if args_json == None:
        command = [curl, plain_text, server, method, obj]
    else:
        command = [curl, plain_text, "-d", args_json, server, method]

    process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    while True:
        output = process.stdout.readline()

        if output == '' and process.poll() is not None:
            break
        if output:
            print(output.strip())

    if process.returncode != 0:
        print("Error:", process.stderr.read())

    process.stdout.close()
    process.stderr.close()