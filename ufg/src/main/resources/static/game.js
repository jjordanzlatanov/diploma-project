/** @type {import('./phaser')} */

// Game Objects Metadata
let gom = [{type: 'burnerDrill', texture: 'burner-drill', width: 60, height: 60}, {type: 'pipe', texture: 'pipe-cross', width: 30, height: 30}]

class Action {
    constructor(){}

    clear(){
        this.type = null
        this.objectType = null
        this.objectWidth = null
        this.objectHeight = null
        this.startX = null
        this.startY = null
        this.buildSprite.setActive(false).setVisible(false)
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

    setBuildSprite(sprite) {
        this.buildSprite = sprite.setOrigin(0).setActive(false).setVisible(false)
        return this
    }

    setType(type){
        this.type = type
        return this
    }

    setObjectType(objectType){
        this.objectType = objectType
        return this
    }

    setObjectWidth(objectWidth){
        this.objectWidth = objectWidth;
        return this
    }

    setObjectHeight(objectHeight){
        this.objectHeight = objectHeight;
        return this
    }

    setObject(ObjectIndex){
        this.type = 'build'
        this.objectType = gom[ObjectIndex].type
        this.objectWidth = gom[ObjectIndex].width
        this.objectHeight = gom[ObjectIndex].height
        this.buildSprite.setTexture(gom[ObjectIndex].texture).setAlpha(0.5).setActive(true).setVisible(true)
    }

    setStartX(){
        let boxX = this.x
        let closestX = Math.round(boxX / 30) * 30
        let floor = Math.floor(boxX / 30) * 30
        let farthestX = ((0.5 - (((closestX  - floor) % 4) / 4)) * 60) + floor

        let widthCoef = (this.objectWidth % 4) / 4
        let coef = (closestX - farthestX) / 30
        let centerX = closestX - (coef * (30 * widthCoef))
        let projectX = centerX - (this.objectWidth / 2)

        this.startX = projectX
        return this
    }
    
    setStartY(){
        let boxY = this.y
        let closestY = Math.round(boxY / 30) * 30
        let floor = Math.floor(boxY / 30) * 30
        let farthestY = ((0.5 - (((closestY  - floor) % 4) / 4)) * 60) + floor

        let heightCoef = (this.objectHeight % 4) / 4
        let coef = (closestY - farthestY) / 30
        let centerY = closestY - (coef * (30 * heightCoef))
        let projectY = centerY - (this.objectHeight / 2)

        this.startY = projectY
        return this
    }

    setBuildSpriteX() {
        this.buildSprite.setX(this.startX)
        return this
    }

    setBuildSpriteY() {
        this.buildSprite.setY(this.startY)
        return this
    }

    getX(){
        return this.x
    }
    
    getY(){
        return this.y
    }

    getBuildSprite() {
        return this.buildSprite
    }

    getBuildSpriteActive() {
        return this.buildSprite.active
    }

    getType(){
        return this.type
    }

    getObjectType(){
        return this.objectType
    }

    getObjectWidth(){
        return this.objectWidth;
    }

    getObjectHeight(){
        return this.objectHeight;
    }

    getStartX(){
        return this.startX
    }
    
    getStartY(){
        return this.startY
    }

    update(x, y) {
        this.setX(x).setY(y)

        if(this.getBuildSpriteActive()) {
            this.setStartX().setStartY().setBuildSpriteX().setBuildSpriteY()
        }
    }
}

let initialGameObjects = null
let socket = null
let game = null
let gameObjects = []
let action = new Action()

class GameScene extends Phaser.Scene {
    constructor(){
        super('game')
    }

    init(){
        
    }

    preload(){
        this.load.image('grass', '/assets/grass.png')

        this.load.image('coal-ore', '../assets/coal-ore.png')
        this.load.image('iron-ore', '../assets/iron-ore.png')
        this.load.image('copper-ore', '../assets/copper-ore.png')
        this.load.image('burner-drill', '../assets/burner-drill.png')
        this.load.image('stone-furnace', '../assets/stone-furnace.png')

        this.load.image('pipe-corner-down-left', '../assets/pipe-corner-down-left.png')
        this.load.image('pipe-corner-down-right', '../assets/pipe-corner-down-right.png')
        this.load.image('pipe-corner-up-left', '../assets/pipe-corner-up-left.png')
        this.load.image('pipe-corner-up-right', '../assets/pipe-corner-up-right.png')

        this.load.image('pipe-cross', '../assets/pipe-cross.png')

        this.load.image('pipe-straight-horizontal', '../assets/pipe-straight-horizontal.png')
        this.load.image('pipe-straight-vertical', '../assets/pipe-straight-vertical.png')

        this.load.image('pipe-t-down', '../assets/pipe-t-down.png')
        this.load.image('pipe-t-left', '../assets/pipe-t-left.png')
        this.load.image('pipe-t-right', '../assets/pipe-t-right.png')
        this.load.image('pipe-t-up', '../assets/pipe-t-up.png')
    }

    create(){
        initialGameObjects.forEach(gameObject => {
            gameObjects.push({sprite: this.add.sprite(gameObject.startX, gameObject.startY, gameObject.texture).setOrigin(0), uuid: gameObject.uuid})
        })

        action.setBuildSprite(this.add.sprite(0, 0, 'grass'))

        this.input.keyboard.on('keyup', (key) => {
            action.clear()

            switch (key.keyCode) {
                case Phaser.Input.Keyboard.KeyCodes.ONE:
                    action.setObject(0)
                    break

                case Phaser.Input.Keyboard.KeyCodes.TWO:
                    action.setObject(1)
                    break

                case Phaser.Input.Keyboard.KeyCodes.D:
                    action.setType('destroy')
                    socket.emit('clickD', action)
                    break
            }
        })

        this.input.mouse.disableContextMenu()

        this.input.on('pointerup', (pointer) => {
            if(pointer.leftButtonReleased()){
                if(action.getType() === 'build'){
                    socket.emit('clickLeft', action)
                }
            }
        })

        socket.on('build', (gameObject) => {
            gameObjects.push({sprite: this.add.sprite(gameObject.startX, gameObject.startY, gameObject.texture).setOrigin(0), uuid: gameObject.uuid})
        })

        socket.on('destroy', (gameObject) => {
            let index = gameObjects.findIndex((element) => (element.uuid === gameObject.uuid))

            gameObjects[index].sprite.destroy()
            gameObjects.splice(index, 1)
        })

        socket.on('updateTexture', (updatedGameObjects) => {
            updatedGameObjects.forEach(gameObject => {
                let index = gameObjects.findIndex((element) => (element.uuid === gameObject.uuid))

                gameObjects[index].sprite.setTexture(gameObject.texture)
            })
        })
    }

    update(){
        action.update(this.input.activePointer.worldX, this.input.activePointer.worldY)
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

socket.on('game', (newGameObjects) => {
    initialGameObjects = newGameObjects
    game = new Phaser.Game(config)
})

// window.addEventListener('resize', () => {
//     window.location.reload()
// })