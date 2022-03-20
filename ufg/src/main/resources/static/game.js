/** @type {import('./phaser')} */

let initialMap = null
let socket = null
let game = null

class GameScene extends Phaser.Scene{
    constructor(){
        super('game')
    }

    init(){
        
    }

    preload(){
        this.load.image('grass', '/assets/grass.png')
        this.load.image('coal', '../assets/coal.png')
        this.load.image('iron-ore', '../assets/iron-ore.png')
        this.load.image('copper-ore', '../assets/copper-ore.png')
        this.load.image('burner-drill', '../assets/burner-drill.png')
        this.load.image('stone-furnace', '../assets/stone-furnace.png')
        this.load.image('pipe-corner-down-left', '../assets/pipe-corner-down-left.png')
        this.load.image('pipe-corner-down-right', '../assets/pipe-corner-down-right.png')
        this.load.image('pipe-corner-up-left', '../assets/pipe-corner-up-left.png')
        this.load.image('pipe-corner-up-right', '../assets/pipe-corner-up-right.png')
        this.load.image('pipe-straight-horizontal', '../assets/pipe-straight-horizontal.png')
        this.load.image('pipe-straight-vertical', '../assets/pipe-straight-vertical.png')
    }

    create(){
        initialMap.forEach(mapObject => {
            this.add.sprite(mapObject.startX, mapObject.startY, mapObject.texture).setOrigin(0)
        })

        this.input.mouse.disableContextMenu()

        this.input.on('pointerup', (pointer) => {
            if(pointer.leftButtonReleased()){
                socket.emit('clickLeft', {x: this.input.activePointer.worldX, y: this.input.activePointer.worldY})
            }
        })

        socket.on('build', (mapObject) => {
            this.add.sprite(mapObject.startX, mapObject.startY, mapObject.texture).setOrigin(0)
        })
    }

    update(){

    }
}

let config = {
    type: Phaser.AUTO,
    width: window.innerWidth,
    height: window.innerHeight,
    backgroundColor: '#000000',
    scene: [GameScene]
}

socket = io('http://localhost:3000')

socket.on('connect', () => {
    socket.emit('username', username)
})

socket.on('map', (map) => {
    initialMap = map
    game = new Phaser.Game(config)
})

// window.addEventListener('resize', () => {
//     window.location.reload()
// })