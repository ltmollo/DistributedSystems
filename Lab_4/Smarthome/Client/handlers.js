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

const handleMP3Player = async (deviceName) => {
    const stub = stubs[deviceName]

    console.log('Commands: getState, turnOn, turnOff, setVolumne, getVolumne, getSong, setSong, pause, unpause')
    const command = prompt('>>')
    
    if (await handleDeviceCommands(command, stub)) return
    else if (await handleSpeakerCommands(command, stub)) return
    else if (await handleMp3PlayerCommands(command, stub)) return
    else {
        console.log('[handleMP3Player] unknown command')
    }
}

const handleCoffeeMaker = async (deviceName) => {
    const stub = stubs[deviceName]

    console.log('Commands: getState, turnOn, turnOff, getWaterLevel, getCoffeeLevel, getCurrentCoffee, setCoffee, makeCoffee,')
    console.log('getCoffeeTypes, getCoffeeStrengths, addWater, addCoffee')
    const command = prompt('>>')
    
    if (await handleDeviceCommands(command, stub)) return
    else if (await handleCoffeeMakerCommands(command, stub)) return
    else {
        console.log('[handleCoffeeMaker] unknown command')
    }
}

const handleThermometer = async (deviceName) => {
    const stub = stubs[deviceName]

    console.log('Commands: getMeasurement, countInFahrenheit')
    const command = prompt('>>')
    
    if (await handleSensorCommands(command, stub)) return
    else if (await handleThermometerCommands(command, stub)) return
    else {
        console.log('[handleThermometer] unknown command')
    }
}

const handleCO = async (deviceName) => {
    const stub = stubs[deviceName]

    console.log('Commands: getMeasurement, alarm')
    const command = prompt('>>')
    
    if (await handleSensorCommands(command, stub)) return
    else if (await handleCOCommands(command, stub)) return
    else {
        console.log('[handleCO] unknown command')
    }
    
}

const handleCOCommands = async (command, stub) => {
    switch (command) {
        case 'alarm':
            console.log(await stub.alarm())
            return true
    }
    return false
}

const handleThermometerCommands = async (command, stub) => {
    switch (command) {
        case 'countInFahrenheit':
            console.log(await stub.countInFahrenheit())
            return true
    }
    return false
}


const handleCoffeeMakerCommands = async (command, stub) => {
    switch (command) {
        case 'getWaterLevel':
            console.log(await stub.getWaterLevel())
            return true

        case 'getCoffeeLevel':
        console.log(await stub.getCoffeeLevel())
        return true

        case 'getCurrentCoffee':
            console.log(await stub.getCurrentCoffee())
            return true

        
        case 'setCoffee':
            const type = prompt('CoffeeType (ESPRESSO, CAPPUCINO, LATTEMACCHIATO, WITMILK): ')
            const strength = prompt('CoffeeStrength (LIGHT, MEDIUM, STRONG): ')

            const coffeeType = Smarthome.CoffeeType[type]
            const coffeeStrength = Smarthome.CoffeeStrength[strength]

            if (!coffeeType) {
                console.log("Wrong coffeeType, choose between: ESPRESSO, CAPPUCINO, LATTEMACCHIATO, WITMILK")
                return true
            }

            if(!coffeeStrength) {
                console.log("Wrong coffeeStrength, choose between: LIGHT, MEDIUM, STRONG")
                return true
            }

            
            const coffee = new Smarthome.Coffee(coffeeType, coffeeStrength)
            console.log(await stub.setCoffee(coffee))

            return true

        case 'makeCoffee':
            console.log(await stub.makeCoffee())
            return true

        case 'getCoffeeTypes':
            console.log(await stub.getCoffeeTypes())
            return true

        case 'getCoffeeStrengths':
            console.log(await stub.getCoffeeStrengths())
            return true

        case 'addWater':
            const waterLevel = prompt("Water [ml]: ")
            if (waterLevel < 0) {
                console.log("Amount of water must be positive!")
                return true
            }
            console.log(await stub.addWater(waterLevel))
            return true

        case 'addCoffee':
            const coffeeLevel = prompt("Coffee [g]: ")
            if (coffeeLevel < 0) {
                console.log("Amount of coffee must be positive!")
                return true
            }
            console.log(await stub.addCoffee(coffeeLevel))
            return true
    }
    return false
}

const handleMp3PlayerCommands = async (command, stub) => {
    switch (command) {
        case 'getSong':
            console.log(await stub.getSong())
            return true
        case 'setSong':
            const title = prompt('Title: ')
            const artist = prompt('Artist: ')
            const duration = prompt('Duration: ')
            const genreName = prompt('Genre (ROCK, POP, JAZZ, RAP, METAL): ')

            const genre = Smarthome.Genre[genreName]

            if (!genre) {
                console.log("Wrong Genre, choose between: ROCK, POP, JAZZ, RAP, METAL")
                return true
            }

            if (title == "" || artist == "" || duration <= 0) {
                console.log("Invalid title, artist or duration format!")
                return true
            }
            const song = new Smarthome.Song(title, artist, duration, genre)
            console.log(await stub.setSong(song))

            return true
        case 'pause':
            console.log(await stub.pause())
            return true

        case 'unpause':
            console.log(await stub.unPause())
            return true
    }
    return false
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
            const channelName = prompt('Channel: ')
            const channel = Smarthome.Channel[channelName]

            if (!channel) {
                console.log("Unknow channel! Try getChannels")
                return true
            }

            console.log(await stub.setChannel(channel))
            return true
        case 'getChannel':
            console.log((await stub.getChannel()).name)
            return true
        case 'getChannels':
            console.log(await stub.getChannels())
            return true
    }
    return false
}

const handleSensorCommands = async (command, stub) => {
    switch (command) {
        case 'getMeasurement':
            console.log(await stub.getMeasurement())
            return true
    }
    return false
}



exports.initializeStubs = initializeStubs
exports.handleTelevision = handleTelevision
exports.handleMP3Player = handleMP3Player
exports.handleCoffeeMaker = handleCoffeeMaker
exports.handleThermometer = handleThermometer
exports.handleCO = handleCO