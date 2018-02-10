//
//  ViewController.swift
//  Watermark
//
//  Created by Константин Петкевич on 03.10.2017.
//  Copyright © 2017 Konstantin Petkevich. All rights reserved.
//

import Cocoa

class ViewController: NSViewController {
    
    @IBOutlet weak var imageWell: NSImageView!
    
    @IBAction func buttonOpenImage(_ sender: Any) {
        
        let rpFilePicker: NSOpenPanel = NSOpenPanel()
        
        rpFilePicker.allowsMultipleSelection = false
        rpFilePicker.canChooseFiles = true
        rpFilePicker.canChooseDirectories = false
        
        rpFilePicker.runModal()
        
        let chosenFile = rpFilePicker.url
        
        if (chosenFile != nil) {
            //there is a file
            
            let picImport = NSImage(contentsOf: chosenFile!)
            
            imageWell.image = picImport

        }
        
    }
    
    @IBAction func buttonEdit(_ sender: Any) {
        let image = imageWell.image!
        let newImage = image.increaseColorIntensity(byPercent: 50) // percentage is not used
        imageWell.image = newImage
    }
    
    
    @IBAction func buttonSave(_ sender: Any) {
//        let savePanel = NSSavePanel()
//        savePanel.canCreateDirectories = true
//        savePanel.showsTagField = false
//        savePanel.nameFieldStringValue = "image.png"
//        savePanel.level = NSWindow.Level(rawValue: Int(CGWindowLevelForKey(.modalPanelWindow)))
//        savePanel.begin { (result) in
//            if result == NSApplication.ModalResponse.OK {
//                guard let url = savePanel.url else { return }
//
//                let bitmapRep = NSBitmapImageRep(data: (self.imageWell?.image?.tiffRepresentation)!)
//                let data = bitmapRep?.representation(using: NSBitmapImageRep.FileType.png, properties: [NSBitmapImageRep.PropertyKey.compressionFactor : 1])
//                do {
//                    try data?.write(to: url, options: .atomicWrite)
//                } catch {
//                    print(error.localizedDescription)
//                }
//            }
//        }
        
        

        let file = "image.png"
        //let myURL = URL(string: "File:///Users/konstafer/ITMO-FILES/image.png")!
        if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {

            let myURL = dir.appendingPathComponent(file)

            if imageWell.image != nil {
                let bMImg = NSBitmapImageRep(data: (imageWell?.image?.tiffRepresentation)!)
                let dataToSave = bMImg?.representation(using: NSBitmapImageRep.FileType.png, properties: [NSBitmapImageRep.PropertyKey.compressionFactor : 1])
                do {
                    try dataToSave?.write(to: myURL)
                }catch {
                Swift.print("Error")
                }
            }
        }
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    override var representedObject: Any? {
        didSet {
        // Update the view, if already loaded.
        }
    }


}

