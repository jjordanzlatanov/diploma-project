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

let key1

class GameScene extends Phaser.Scene{
    constructor(){
        super('game')
    }

    init(){

    }

    preload(){
        this.load.image('grass', '../assets/grass.png')
        this.load.image('coal', '../assets/coal.png')
        this.load.image('iron-ore', '../assets/iron-ore.png')
        this.load.image('copper-ore', '../assets/copper-ore.png')
        
        
        this.load.image('miner', '../assets/miner.png')
    }

    create(){
        key1 = this.input.keyboard.addKey(Phaser.Input.Keyboard.KeyCodes.ONE)

        let width = this.sys.game.config.width
        let height = this.sys.game.config.height
        

        for(let x = 0; x < width + 10; x += 128){
            for(let y = 0; y < height + 20; y += 128){
                this.add.sprite(x, y, 'grass')
            }
        }

        for(let x = 110; x < 500; x += 30){
            for(let y = 20; y < 200; y += 30){
                this.add.sprite(x, y, 'coal')
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