/** @type {import('./phaser')} */

class Action {
    constructor(){}

    clear(){
        this.x = null
        this.y = null
        this.objectType = null
        this.type = null
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

    setType(type){
        this.type = type
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

    getType(){
        return this.type
    }
}

let initialGameObjects = null
let socket = null
let game = null

let action = new Action()
// Game Objects Metadata
let gom = [{type: 'burnerDrill', texture: 'burner-drill', width: 60, height: 60}]

let buildSprite = null

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

        buildSprite = this.add.sprite(0, 0, 'grass').setOrigin(0)
        buildSprite.setActive(false).setVisible(false)

        this.input.keyboard.on('keyup', (key) => {
            action.clear();
            buildSprite.setActive(false).setVisible(false)

            switch (key.keyCode) {
                case Phaser.Input.Keyboard.KeyCodes.ONE:
                    action.setType('build')
                    action.setObjectType(gom[0].type)
                    buildSprite.setActive(true).setVisible(true).setTexture(gom[0].texture).setAlpha(0.5)
                    break

                case Phaser.Input.Keyboard.KeyCodes.D:
                    socket.emit('clickD', action)
                    break
            }
        })

        this.input.mouse.disableContextMenu()

        this.input.on('pointerup', (pointer) => {
            if(pointer.leftButtonReleased()){
                socket.emit('clickLeft', action)
            }
        })

        socket.on('build', (gameObject) => {
            this.add.sprite(gameObject.startX, gameObject.startY, gameObject.texture).setOrigin(0)
        })
    }

    update(){
        buildSprite.setX(this.input.activePointer.worldX).setY(this.input.activePointer.worldY)
        action.setCords(this.input.activePointer.worldX, this.input.activePointer.worldY)
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