/** @type {import('./phaser')} */

class Action {
    constructor(){}

    clear(){
        this.x = null
        this.y = null
        this.objectType = null
        this.type = null
        this.objectWidth = null
        this.objectHeight = null
        this.startX = null
        this.startY = null
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

    setStartX(startX){
        this.startX = startX
        return this
    }
    
    setStartY(startY){
        this.startY = startY
        return this
    }

    setStartCords(startX, startY){
        this.startX = startX
        this.startY = startY
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

    setObjectWidth(objectWidth){
        this.objectWidth = objectWidth;
        return this
    }

    setObjectHeight(objectHeight){
        this.objectHeight = objectHeight;
        return this
    }

    getX(){
        return this.x
    }
    
    getY(){
        return this.y
    }

    getStartX(){
        return this.startX
    }
    
    getStartY(){
        return this.startY
    }

    getObjectType(){
        return this.objectType
    }

    getType(){
        return this.type
    }

    getObjectWidth(){
        return this.objectWidth;
    }

    getObjectHeight(){
        return this.objectHeight;
    }

    getRoundX(){
        return Math.floor(this.x / 30) * 30
    }

    getRoundY(){
        return Math.floor(this.y / 30) * 30
    }

    getProjectX(){
        let boxX = this.x
        let closestX = Math.round(boxX / 30) * 30
        let floor = Math.floor(boxX / 30) * 30
        let farthestX = ((0.5 - (((closestX  - floor) % 4) / 4)) * 60) + floor

        let widthCoef = ((this.objectWidth % 4) / 4)
        let coef = ((closestX - farthestX) / 30)
        let centerX = closestX - (coef * (30 * widthCoef))
        let projectX = centerX - (this.objectWidth / 2)

        return projectX
    }

    getProjectY(){
        let boxY = this.y
        let closestY = Math.round(boxY / 30) * 30
        let floor = Math.floor(boxY / 30) * 30
        let farthestY = ((0.5 - (((closestY  - floor) % 4) / 4)) * 60) + floor

        let heightCoef = ((this.objectHeight % 4) / 4)
        let coef = ((closestY - farthestY) / 30)
        let centerY = closestY - (coef * (30 * heightCoef))
        let projectY = centerY - (this.objectHeight / 2)

        return projectY
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
                    action.setObjectWidth(gom[0].width)
                    action.setObjectHeight(gom[0].height)
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
                action.setStartCords(action.getProjectX(), action.getProjectY())
                socket.emit('clickLeft', action)
            }
        })

        socket.on('build', (gameObject) => {
            this.add.sprite(gameObject.startX, gameObject.startY, gameObject.texture).setOrigin(0)
        })
    }

    update(){
        action.setCords(this.input.activePointer.worldX, this.input.activePointer.worldY)

        if(buildSprite.active){
            buildSprite.setX(action.getProjectX()).setY(action.getProjectY())
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