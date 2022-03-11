/** @type {import("./phaser")} */

class MineArea{
    constructor(type, startX, startY, endX, endY){
        this.type = type
        this.startX = startX
        this.startY = startY
        this.endX = endX
        this.endY = endY
    }
}

let coalArea = new MineArea('coal', 110, 20, 500, 200)
let ironArea = new MineArea('raw iron', 110, 20, 500, 200)
let copperArea = new MineArea('raw copper', 110, 20, 500, 200)

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
        this.load.image('grass1', '../assets/grass1.png')
        this.load.image('grass2', '../assets/grass2.png')
        this.load.image('grass3', '../assets/grass3.png')
        this.load.image('grass4', '../assets/grass4.png')
        this.load.image('grass5', '../assets/grass5.png')

        this.load.image('coal', '../assets/coal.png')
        this.load.image('iron-ore', '../assets/iron-ore.png')
        this.load.image('copper-ore', '../assets/copper-ore.png')
    }

    create(){
        key1 = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.ONE)

        let width = this.sys.game.config.width
        let height = this.sys.game.config.height
        

        for(let x = 0; x < width + 10; x += 30){
            for(let y = 0; y < height + 10; y += 30){
                this.add.sprite(x, y, 'grass' + getRandomInt(5)).setOrigin(0, 0)
            }
        }

        for(let x = 0; x < 500; x += 30){
            for(let y = 0; y < 200; y += 30){
                this.add.sprite(x, y, 'coal').setOrigin(0, 0)
            }
        }

        for(let x = 630; x < 1100; x += 30){
            for(let y = 20; y < 200; y += 30){
                this.add.sprite(x, y, 'iron-ore')
            }
        }

        for(let x = 1230; x < 1700; x += 30){
            for(let y = 20; y < 200; y += 30){
                this.add.sprite(x, y, 'copper-ore')
            }
        }
    }

    update(time, delta){
        if(key1.isDown){
            console.log('1')
        }
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