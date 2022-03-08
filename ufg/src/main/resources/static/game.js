/** @type {import("./phaser")} */

class GameScene extends Phaser.Scene{
    constructor(){

    }

    preload(){
        
    }

    create(){

    }

    update(time, delta){

    }
}

let config = {
    type: Phaser.AUTO,
    width: window.innerWidth,
    height: window.innerHeight,
    backgroundColor: '#000',
    scene: [GameScene]
}

let game = new Phaser.Game(config)

window.addEventListener('resize', () => {
    window.location.reload()
})