/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package tntrun.selectionget;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerSelection {

	private WEIntegration weintegration = null;
	private OwnLocations ownlocations = new OwnLocations();

	public PlayerSelection() {
		if (Bukkit.getPluginManager().getPlugin("WorldEdit") != null) {
			weintegration = new WEIntegration();
		}
	}

	public PlayerCuboidSelection getPlayerSelection(Player player) {
		// try to get own locations
		Location[] locs = ownlocations.getLocations(player);
		if (locs != null) {
			return new PlayerCuboidSelection(locs[0], locs[1]);
		}
		// now check worldedit selection
		if (weintegration != null) {
			locs = weintegration.getLocations(player);
			if (locs != null) {
				return new PlayerCuboidSelection(locs[0], locs[1]);
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public void setSelectionPoint1(Player player) {
		ownlocations.putPlayerLoc1(player.getName(), player.getTargetBlock((HashSet<Material>) null, 30).getLocation());
	}

	@SuppressWarnings("deprecation")
	public void setSelectionPoint2(Player player) {
		ownlocations.putPlayerLoc2(player.getName(), player.getTargetBlock((HashSet<Material>) null, 30).getLocation());
	}

	public void clearSelectionPoints(Player player) {
		ownlocations.clearPoints(player.getName());
	}

}
