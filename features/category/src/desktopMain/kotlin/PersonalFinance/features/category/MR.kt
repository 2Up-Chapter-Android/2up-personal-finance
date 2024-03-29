package PersonalFinance.features.category

import dev.icerock.moko.resources.AssetResource
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.FileResource
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.StringResource

public actual object MR {
    public actual object strings : ResourceContainer<StringResource>
    public actual object plurals : ResourceContainer<PluralsResource>
    public actual object images : ResourceContainer<ImageResource>
    public actual object fonts : ResourceContainer<FontResource>
    public actual object files : ResourceContainer<FileResource>
    public actual object colors : ResourceContainer<ColorResource>
    public actual object assets : ResourceContainer<AssetResource>

}