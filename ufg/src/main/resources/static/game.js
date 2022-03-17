/** @type {import('./phaser')} */

let socket = io('http://localhost:3000')

socket.on('connect', () => {
    console.log('Connection established')
})

socket.on('textMessage', (message) => {
    console.log(message)
})

socket.on('myBroadcast', (data) => {
    console.log(data)
})

socket.on('ObjectMessage', (data) => {
    console.log(data.userName + ' ' + data.message)
})

socket.emit('ObjectMessage', {userName: 'john', message: 'hi im john'})


function getRandomInt(max) {
    let number = Math.floor(Math.random() * max)
    
    while(number == 0){
        number = Math.floor(Math.random() * max)
    }

    return number
}

let key1

class GameScene extends Phaser.Scene{
    constructor(){
        super('game')
    }

    init(){
        
    }

    preload(){
        this.load.image('grass1', '/images/grass1.png')
//        this.load.image('grass2', '../assets/grass2.png')
//        this.load.image('grass3', '../assets/grass3.png')
//        this.load.image('grass4', '../assets/grass4.png')
//        this.load.image('grass5', '../assets/grass5.png')
//        this.load.image('pipe-corner-down-left', '../assets/pipe-corner-down-left.png')
//        this.load.image('pipe-corner-down-right', '../assets/pipe-corner-down-right.png')
//        this.load.image('pipe-corner-up-left', '../assets/pipe-corner-up-left.png')
//        this.load.image('pipe-corner-up-right', '../assets/pipe-corner-up-right.png')
//        this.load.image('pipe-straight-horizontal', '../assets/pipe-straight-horizontal.png')
//        this.load.image('pipe-straight-vertical', '../assets/pipe-straight-vertical.png')
//
//
//        this.load.image('coal', '../assets/coal.png')
//        this.load.image('iron-ore', '../assets/iron-ore.png')
//        this.load.image('copper-ore', '../assets/copper-ore.png')
//
//        this.load.image('burner-drill', '../assets/burner-drill.png')
//        this.load.image('stone-furnace', '../assets/stone-furnace.png')
    }

    create(){
        //key1 = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.ONE)

        let width = this.sys.game.config.width
        let height = this.sys.game.config.height

        for(let x = 0; x < width + 10; x += 30){
            for(let y = 0; y < height + 10; y += 30){
                this.add.sprite(x, y, 'grass1').setOrigin(0, 0)
            }
        }
//
//        for(let x = 0; x < 500; x += 30){
//            for(let y = 0; y < 200; y += 30){
//                this.add.sprite(x, y, 'coal').setOrigin(0, 0)
//            }
//        }
//
//        for(let x = 630; x < 1100; x += 30){
//            for(let y = 0; y < 200; y += 30){
//                this.add.sprite(x, y, 'iron-ore').setOrigin(0, 0)
//            }
//        }
//
//        for(let x = 1230; x < width + 10; x += 30){
//            for(let y = 0; y < 200; y += 30){
//                this.add.sprite(x, y, 'copper-ore').setOrigin(0, 0)
//            }
//        }

        // this.load.image('pipe-corner-down-left', '../assets/pipe-corner-down-left.png')
        // this.load.image('pipe-corner-down-right', '../assets/pipe-corner-down-right.png')
        // this.load.image('pipe-corner-up-left', '../assets/pipe-corner-up-left.png')
        // this.load.image('pipe-corner-up-right', '../assets/pipe-corner-up-right.png')
        // this.load.image('pipe-corner-straight-horizontal', '../assets/pipe-corner-straight-horizontal.png')
        // this.load.image('pipe-corner-straight-vertical', '../assets/pipe-corner-straight-vertical.png')

//        this.add.sprite(60, 90, 'pipe-straight-vertical').setOrigin(0, 0)
//        this.add.sprite(60, 120, 'pipe-straight-vertical').setOrigin(0, 0)
//        this.add.sprite(60, 150, 'pipe-straight-vertical').setOrigin(0, 0)
//        this.add.sprite(60, 180, 'pipe-straight-vertical').setOrigin(0, 0)
//        this.add.sprite(60, 210, 'pipe-corner-up-right').setOrigin(0, 0)
//        this.add.sprite(90, 210, 'pipe-straight-horizontal').setOrigin(0, 0)
//        this.add.sprite(120, 210, 'pipe-straight-horizontal').setOrigin(0, 0)
//        this.add.sprite(150, 210, 'pipe-straight-horizontal').setOrigin(0, 0)
//        this.add.sprite(180, 210, 'pipe-straight-horizontal').setOrigin(0, 0)
//
//        this.add.sprite(30, 30, 'burner-drill').setOrigin(0, 0)
//        this.add.sprite(90, 30, 'burner-drill').setOrigin(0, 0)
//        this.add.sprite(150, 30, 'burner-drill').setOrigin(0, 0)
//
//        this.add.sprite(270, 90, 'burner-drill').setOrigin(0, 0)
//        this.add.sprite(330, 90, 'burner-drill').setOrigin(0, 0)
//        this.add.sprite(390, 90, 'burner-drill').setOrigin(0, 0)
//
//        this.add.sprite(810, 90, 'burner-drill').setOrigin(0, 0)
//        this.add.sprite(870, 90, 'burner-drill').setOrigin(0, 0)
//
//        this.add.sprite(1230, 90, 'burner-drill').setOrigin(0, 0)
//        this.add.sprite(1290, 90, 'burner-drill').setOrigin(0, 0)
//        this.add.sprite(1350, 90, 'burner-drill').setOrigin(0, 0)
//
//        this.add.sprite(200, 200, 'stone-furnace').setOrigin(0, 0)
//        this.add.sprite(260, 200, 'stone-furnace').setOrigin(0, 0)
//
//        this.add.sprite(700, 200, 'stone-furnace').setOrigin(0, 0)
//        this.add.sprite(940, 200, 'stone-furnace').setOrigin(0, 0)
//
//        this.add.sprite(1400, 200, 'stone-furnace').setOrigin(0, 0)

    }

    update(){
        // if(key1.isDown){
        //     console.log('1')
        // }
    }
}

let config = {
    type: Phaser.AUTO,
    width: window.innerWidth,
    height: window.innerHeight,
    backgroundColor: '#000000',
    scene: [GameScene]
}

let game = new Phaser.Game(config)

window.addEventListener('resize', () => {
    window.location.reload()
})