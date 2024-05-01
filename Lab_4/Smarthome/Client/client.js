const Ice = require("ice").Ice;
const Smarthome = require('./generated/smarthome').Smarthome;
const prompt = require('prompt-sync')()
const devices = require('./commons/devices').devices;
const servers = require('./commons/config').config;
const { initializeStubs, handleTelevision, handleMP3Player, handleCoffeeMaker, handleThermometer, handleCO } = require('./handlers');


const getDeviceList = async (communicator) => {
    const proxy = communicator.stringToProxy('deviceManager/Home : ' + servers[1])
    const deviceManager = await Smarthome.IDeviceManagerPrx.checkedCast(proxy)
    const deviceList = await deviceManager.getDeviceList()
    deviceList.forEach(device => {
        deviceName = device.name.toString()
        devices[deviceName] = {type: device.type.name, server: device.server}
    })
    printDevices()
}

const printDevices = () => {
    console.log('----- Devices -----')
    console.log(devices)
    console.log('-------------------')
}
 
(async function()
{
    let communicator;
    try
    {
        communicator = Ice.initialize();
        await getDeviceList(communicator)
        await initializeStubs(communicator)
        while (true) {
            console.log('Enter "devices" to list devices, or device name to choose a device, or "stop" to exit')
            let entry = prompt('>>> ');
            switch (entry) {
                case 'stop':
                    return 0
                
                case 'devices':
                    printDevices()
                    break
                
                default:
                    let device = devices[entry]

                    if (!device) {
                        console.log("Unknow device, please select one from provided")
                        break
                    }
                    
                    let deviceType = device.type

                    try {
                    switch(deviceType) {
                        case 'TELEVISION':
                            await handleTelevision(entry)
                            break
                        
                        case 'MP3PLAYER':
                            await handleMP3Player(entry)
                            break

                        case 'THERMOMETER':
                            await handleThermometer(entry)
                            break

                        case 'CO':
                            await handleCO(entry)
                            break
                    }
                } catch (e) {
                    console.log(e)
                }

            }
        }
        
    }
    catch(ex)
    {
        console.log(ex.toString());
        process.exitCode = 1;
    }
    finally
    {
        console.log("Exiting")
        if(communicator)
        {
            await communicator.destroy();
        }
    }
}());