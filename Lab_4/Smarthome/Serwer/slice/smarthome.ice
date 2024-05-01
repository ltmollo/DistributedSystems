module Smarthome {

    exception SmarthomeException {
        string message;
    };

    exception TurnDeviceException extends SmarthomeException {};

    interface IDevice {
        idempotent bool getState();
        bool turnOn() throws TurnDeviceException;
        bool turnOff() throws TurnDeviceException;
    };


    exception InvalidVolumeException extends SmarthomeException {};

    interface ISpeaker extends IDevice {
        idempotent int getVolume();

        idempotent bool setVolume(int volume) throws InvalidVolumeException;
    };

    enum Channel {
        TVP1,
        TVP2,
        TVPINFO,
        TVN,
        POLSAT,
        TVTRWAM
    };
    exception InvalidChannelException extends SmarthomeException {};

    ["java:type:java.util.ArrayList<Channel>"]
    sequence <Channel> channels;

    interface ITelevision extends ISpeaker {
        idempotent Channel getChannel();
        idempotent channels getChannels();

        idempotent bool setChannel(Channel channel) throws InvalidChannelException;
    };

     struct Movie {
            string title;
            string director;
            string mainActor;
            double duration;
        };
        exception InvalidMovieException extends SmarthomeException {};

    interface IDVD extends ISpeaker {
            idempotent Movie getMovie();

            idempotent bool setMovie(Movie movie) throws InvalidMovieException;
        };

    enum Genre {
            ROCK,
            POP,
            JAZZ,
            RAP,
            METAL
        };
        exception InvalidGenreException extends SmarthomeException {};

    struct Song {
        string title;
        string artist;
        double duration;
        Genre genre;
    };
    exception InvalidSongException extends SmarthomeException {};
    exception PauseMP3Exception extends SmarthomeException {};

    interface IMP3Player extends ISpeaker {
        idempotent Song getSong();

        idempotent bool setSong(Song song) throws InvalidSongException;

        idempotent bool pause() throws PauseMP3Exception;
        idempotent bool unpause() throws PauseMP3Exception;
    };


interface ISensor{
    double getMeasurement();
};

interface IThermometer extends ISensor {
    double countInFahrenheit();
};

interface ICO extends ISensor {
    bool alarm();
};

    exception CoffeeUnderflowException extends SmarthomeException {};
    exception WaterUnderflowException extends SmarthomeException {};
    exception InvalidAmountException extends SmarthomeException{};

    enum CoffeeType {
        ESPRESSO,
        CAPPUCINO,
        LATTEMACCHIATO,
        WITMILK
    };

    enum CoffeeStrength{
        LIGHT,
        MEDIUM,
        STRONG
    };

    struct Coffee {
        CoffeeType type;
        CoffeeStrength strength;
    };
    exception InvalidCoffeeException extends SmarthomeException {};
    ["java:type:java.util.ArrayList<CoffeeType>"]
    sequence <CoffeeType> coffeeTypes;
    ["java:type:java.util.ArrayList<CoffeeStrength>"]
        sequence <CoffeeStrength> coffeeStrengths;

    interface ICoffeeMaker extends IDevice {
            idempotent int getWaterLevel();
            idempotent int getCoffeeLevel();
            idempotent Coffee getCurrentCoffee();

            idempotent bool setCoffee(Coffee coffee) throws InvalidCoffeeException;
            idempotent Coffee makeCoffee() throws WaterUnderflowException, CoffeeUnderflowException;
            idempotent coffeeTypes getCoffeeTypes();
            idempotent coffeeStrengths getCoffeeStrengths();

            bool addWater(int water) throws InvalidAmountException;
            bool addCoffee(int coffee) throws InvalidAmountException;
        };

    enum DeviceType {
        MP3PLAYER,
        TELEVISION,
        CO,
        THERMOMETER,
        COFFEEMAKER
    };


    struct DeviceInfo {
        string name;
        DeviceType type;
        int server;
    };

    ["java:type:java.util.ArrayList<DeviceInfo>"]
    sequence <DeviceInfo> deviceList;

    interface IDeviceManager {
        idempotent deviceList getDeviceList();
    };
};