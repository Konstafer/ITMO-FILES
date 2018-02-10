//
//  as.swift
//  Watermark
//
//  Created by Константин Петкевич on 16.10.2017.
//  Copyright © 2017 Konstantin Petkevich. All rights reserved.
//

import Foundation
import Cocoa

extension NSImage {
    //implementation of the method with a change in the intensity of interest
    func increaseColorIntensity(byPercent percents: Int) -> NSImage? {
        assert(0...100 ~= percents, "Percents value in 0...100 range") //allowable interest
        guard let cgImageRef = self.cgImage(forProposedRect: nil, context: nil, hints: nil) else {
            return nil
        }
        let intensityIncrease = CGFloat(percents) / 100 //for interest modification
        let width = cgImageRef.width
        let height = cgImageRef.height
        let imageSize = NSSize(width: width, height: height)
        let imageRect = CGRect(origin: .zero, size: imageSize)
        let colorSpace = CGColorSpaceCreateDeviceRGB()
        let bytesPerPixel = 4
        let bytesPerRow = bytesPerPixel * width
        let bitsPerComponent = 8
        let bitmapByteCount = bytesPerRow * height
        let rawData = UnsafeMutablePointer<UInt8>.allocate(capacity: bitmapByteCount)
        let context = CGContext(data: rawData,
                                width: width,
                                height: height,
                                bitsPerComponent: bitsPerComponent,
                                bytesPerRow: bytesPerRow,
                                space: colorSpace,
                                bitmapInfo: CGImageAlphaInfo.premultipliedLast.rawValue | CGBitmapInfo.byteOrder32Big.rawValue)
        context?.draw(cgImageRef, in: imageRect)
        var byteIndex = 0
        while byteIndex < bitmapByteCount {
            rawData[byteIndex + 0] = 255// R channel in byteOrderBig
            //rawData[byteIndex + 0] = UInt8(min(CGFloat(rawData[byteIndex + 0]) * (1 - intensityIncrease), 255))
            //rawData[byteIndex + 1] = UInt8(min(CGFloat(rawData[byteIndex + 1]) * (1 - intensityIncrease), 255)) // G channel in byteOrderBig
            //rawData[byteIndex + 2] = UInt8(min(CGFloat(rawData[byteIndex + 2]) * (1 - intensityIncrease), 255)) // B channel in byteOrderBig
            byteIndex += 4
        }
        let newImage = NSImage(cgImage: context!.makeImage()!, size: imageSize)
        rawData.deinitialize()
        return newImage
    }
}
