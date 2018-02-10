//
//  GenreViewController.swift
//  MusicPlayer
//
//  Created by Константин Петкевич on 08.12.2017.
//  Copyright © 2017 Константин Петкевич. All rights reserved.
//

import UIKit
import MediaPlayer

class GenreViewController: UIViewController {
    
    var musicPlayer = MPMusicPlayerController.applicationMusicPlayer

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func genreButtonTapped(_ sender: UIButton) {
        MPMediaLibrary.requestAuthorization { (status) in
            if status == .authorized {
                self.playGenre(genre: sender.currentTitle!)
            }
        }
      
    }
    
    @IBAction func stopButtonTapped(_ sender: UIButton) {
        musicPlayer.stop()
    }
    
    @IBAction func nextButtonTapped(_ sender: UIButton) {
        musicPlayer.skipToNextItem()
    }
    
    func playGenre(genre: String) {
        
        musicPlayer.stop()
        
        let query = MPMediaQuery()
        let predicate = MPMediaPropertyPredicate(value: genre, forProperty: MPMediaItemPropertyGenre)
        
        query.addFilterPredicate(predicate)
        
        musicPlayer.setQueue(with: query)
        musicPlayer.shuffleMode = .songs
        musicPlayer.play()
    }
}
