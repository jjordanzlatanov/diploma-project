/** @type {import("./phaser")} */

class GameScene extends Phaser.Scene{
    constructor(){
        super('game')
    }

    init(){

    }

    preload(){
        this.load.image('grass', '../assets/grass.png')
    }

    create(){
        let width = this.sys.game.config.width
        let height = this.sys.game.config.height

        for(let x = 0; x < width + 10; x += 39){
            for(let y = 0; y < height + 10; y += 39){
                this.add.sprite(x, y, 'grass')
            }
        }
    }

    update(time, delta){
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