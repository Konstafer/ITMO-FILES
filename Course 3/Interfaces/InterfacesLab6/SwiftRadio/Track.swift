//
//  trackswift
//  InterfecesLab6
//
//  Created by Petkevich Konstantin on 21/12/17.
//  Copyright (c) 2017 Konstafer.com. All rights reserved.
//


import UIKit

//*****************************************************************
// Track struct
//*****************************************************************

struct Track {
	var title: String
	var artist: String
    var artworkImage: UIImage?
    var artworkLoaded = false
    
    init(title: String, artist: String) {
        self.title = title
        self.artist = artist
    }
}
