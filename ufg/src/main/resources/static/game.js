/** @type {import("./phaser")} */

window.addEventListener('resize', () => {
    window.location.reload()
})

let config = {
    type: Phaser.AUTO,
    width: window.innerWidth,
    height: window.innerHeight,
    backgroundColor: '#123456',
    physics: {
        default: 'arcade',
        arcade: {
            gravity: {y: 0, x: 0},
            debug: false
        }
    }
}

let game = new Phaser.Game(config)