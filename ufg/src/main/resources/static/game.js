/** @type {import("./phaser")} */

class GameScene extends Phaser.Scene{
    constructor(){

    }

    preload(){
        
    }

    create(){

    }

    update(){

    }
}

let config = {
    type: Phaser.AUTO,
    width: window.innerWidth,
    height: window.innerHeight,
    backgroundColor: '#000',
    physics: {
        default: 'arcade',
        arcade: {
            gravity: {y: 0, x: 0},
            debug: false
        }
    }
}

let game = new Phaser.Game(config)

window.addEventListener('resize', () => {
    window.location.reload()
})