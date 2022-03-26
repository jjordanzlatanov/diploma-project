/** @type {import('./phaser')} */

class Action {
    constructor(){}

    clear(){
        this.x = null
        this.y = null
        this.objectType = null
        return this
    }

    setX(x){
        this.x = x
        return this
    }
    
    setY(y){
        this.y = y
        return this
    }

    setCords(x, y){
        this.x = x
        this.y = y
        return this
    }

    setObjectType(objectType){
        this.objectType = objectType
        return this
    }

    getX(){
        return this.x
    }
    
    getY(){
        return this.y
    }

    getObjectType(){
        return this.objectType
    }
}

let initialGameObjects = null
let socket = null
let game = null

let action = new Action()
let buildSelection = ['burnerDrill']

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
        initialGameObjects.forEach(gameObject => {
            this.add.sprite(gameObject.startX, gameObject.startY, gameObject.texture).setOrigin(0)
        })

        this.input.keyboard.on('keyup', (key) => {
            action.clear();

            switch (key.keyCode) {
                case Phaser.Input.Keyboard.KeyCodes.ONE:
                    action.setObjectType(buildSelection[0])
                    break

                case Phaser.Input.Keyboard.KeyCodes.D:
                    action.setCords(this.input.activePointer.worldX, this.input.activePointer.worldY)
                    socket.emit('clickD', action)
                    break
            }
        })

        this.input.mouse.disableContextMenu()

        this.input.on('pointerup', (pointer) => {
            if(pointer.leftButtonReleased()){
                action.setCords(this.input.activePointer.worldX, this.input.activePointer.worldY)
                socket.emit('clickLeft', action)
            }
        })

        socket.on('build', (gameObject) => {
            this.add.sprite(gameObject.startX, gameObject.startY, gameObject.texture).setOrigin(0)
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

socket.on('game', (gameObjects) => {
    initialGameObjects = gameObjects
    game = new Phaser.Game(config)
})

// window.addEventListener('resize', () => {
//     window.location.reload()
// })