class Car {
    constructor(name) {
        this.brand = name;
    }

    present() {
        return 'I am' + this.brand
    }
}

const mycar = new Car("Ford");
mycar.present();