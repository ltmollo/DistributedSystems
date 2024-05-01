const {Smarthome} = require("./generated/smarthome");
const stubs = require("./commons/stubs").stubs;
const devices = require("./commons/devices").devices;
const servers = require("./commons/config").config;
const prompt = require("prompt-sync")();

const initializeStubs = async (communicator) => {
    for (const deviceName in devices) {
        if (devices.hasOwnProperty(deviceName)) {
            const { type, server } = devices[deviceName];
            const proxy = communicator.stringToProxy(`${type}/${deviceName} : ${servers[server]}`)
            let device
            switch (type) {
                case 'TELEVISION':
                    device = await Smarthome.ITelevisionPrx.checkedCast(proxy)
                    break
                case 'MP3PLAYER':
                    device = await Smarthome.IMP3PlayerPrx.checkedCast(proxy)
                    break
                case 'COFFEEMAKER':
                    device = await Smarthome.ICoffeeMakerPrx.checkedCast(proxy)
                    break
                case 'THERMOMETER':
                    device = await Smarthome.IThermometerPrx.checkedCast(proxy)
                    break
                case 'CO':
                    device = await Smarthome.ICOPrx.checkedCast(proxy)
                    break
                
                default:
                    throw new Error(`Unsupported type: ${type}`)
            }

            stubs[deviceName] = device
        }
    }
    console.log('[InitializeStubs] stubs initialized')
}


const handleTelevision = async (deviceName) => {
    const stub = stubs[deviceName]

    console.log('Commands: getState, turnOn, turnOff, setVolumne, getVolumne, getChannel, getChannels, setChannel')
    const command = prompt('>>')
    
    if (await handleDeviceCommands(command, stub)) return
    else if (await handleSpeakerCommands(command, stub)) return
    else if (await handleTelevisionCommands(command, stub)) return
    else {
        console.log('[handleTelevision] unknown command')
    }
    
}

const handleDeviceCommands = async (command, stub) => {
    switch (command) {
        case 'getState':
            console.log(await stub.getState())
            return true
        case 'turnOn':
            console.log(await stub.turnOn())
            return true
        case 'turnOff':
            console.log(await stub.turnOff())
            return true
    }
    return false
}

const handleSpeakerCommands = async (command, stub) => {
    switch (command) {
        case 'setVolume':
            const volume = prompt('Volume: ')
            console.log(await stub.setVolume(volume))
            return true
        case 'getVolume':
            console.log(await stub.getVolume())
            return true
    }
    return false
}

const handleTelevisionCommands = async (command, stub) => {
    switch (command) {
        case 'setChannel':
            const channel = prompt('Channel: ')
            console.log(await stub.setChannel(channel))
            return true
        case 'getChannel':
            console.log(await stub.getChannel())
            return true
        case 'getChannels':
            console.log(await stub.getChannels())
            return true
    }
    return false
}



exports.initializeStubs = initializeStubs
exports.handleTelevision = handleTelevision