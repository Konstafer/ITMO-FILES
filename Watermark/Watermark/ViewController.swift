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
        
        var chosenFile = rpFilePicker.url
        
        if (chosenFile != nil) {
            //there is a file
            
            var picImport = NSImage(contentsOf: chosenFile!)
            
            imageWell.image = picImport

        }
        
    }
    
    @IBAction func buttonEdit(_ sender: Any) {
        
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

